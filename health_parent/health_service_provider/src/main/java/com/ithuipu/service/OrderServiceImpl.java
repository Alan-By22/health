package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.dao.MemberDao;
import com.ithuipu.dao.OrderDao;
import com.ithuipu.dao.OrderSettingDao;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.Member;
import com.ithuipu.pojo.Order;
import com.ithuipu.pojo.OrderSetting;
import com.ithuipu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/23---23:35
 * @描述信息 预约的服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    /**
     * 1.检查预约的日期,是否可以预约
     * 2.检查预约的日期,是否约满了
     * 3.防止用户重复预约,同一天,同一个人,同一个套餐---重复预约
     * 4.可以预约--预约的日期---可预约人数+1
     * 5.查询用户是否是会员,不是---自动注册为会员
     * 6.将预约的信息---保存到预约表上
     */
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map map) throws Exception {
        /**1.检查当前日期是否设置预约*/
        String orderDate = (String) map.get("orderDate");
        //2.转换格式
        Date date = DateUtils.parseString2Date(orderDate);
        //3.查询
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        //4.判断
        if (orderSetting == null) {
            //当前日期没有设置预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        /**2.判断当前日期是否约满了*/
        //可预约的人数
        int number = orderSetting.getNumber();
        //已经预约的人数
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            //已经约满了
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        /**3.防止重复预约
         * 4.判断当前用户是否是会员,使用手机号判断
         * */
        String telephone = (String) map.get("telephone");
        //查询会员
        Member member = memberDao.findByTelephone(telephone);
        //判断
        if (member != null) {
            //同一个用户-会员的id,同一天,同一个套餐 --order
            Integer memberId = member.getId();
            String setmealId = (String) map.get("setmealId");
            Order order = new Order();
            order.setOrderDate(date);
            order.setMemberId(memberId);
            order.setSetmealId(Integer.parseInt(setmealId));
            //查询order
            List<Order> list = orderDao.findByCondition(order);
            if (list != null && list.size() > 0) {
                //已经预约了
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }

        //可以预约--修改预约设置中可预约的人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //如果不是会员,自动注册
        if (member == null) {
            //当前用户不是会员，需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        //保存预约信息到预约表--order
        Order order = new Order(
                member.getId()
                , date
                , (String) map.get("orderType")
                , (String) map.get("orderStatus")
                , Integer.parseInt((String) map.get("setmealId"))
        );
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    /**
     * 查询
     */
    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if (map != null) {
            Date orderDate = (Date) map.get("orderDate");
            String date2String = DateUtils.parseDate2String(orderDate);
            map.put("orderDate", date2String);
        }
        return map;
    }
}
