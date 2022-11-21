package com.ithuipu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuipu.constant.RedisConstant;
import com.ithuipu.dao.SetmealDao;
import com.ithuipu.entity.PageResult;
import com.ithuipu.entity.QueryPageBean;
import com.ithuipu.pojo.Setmeal;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/15---15:33
 * @描述信息
 */

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    /**
     * 注入dao
     */
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 取jedis
     */
    @Autowired
    private JedisPool jedisPool;

    /**从属性文件读取输出目录的路径*/
    @Value("${out_put_path}")
    private String outputpath ;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    /**
     * 新增
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkitemIds) {
        //1.添加套餐,回显id
        setmealDao.insert(setmeal);
        //2.维护关联关系
        if (checkitemIds != null && checkitemIds.length > 0) {
            setSetmealAndCheckGroup(setmeal.getId(), checkitemIds);
        }
        //3.将添加的套餐的图片添加到redis中set集合
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

        //生成一个套餐的静态页面 + 套餐详情的静态页面
        generateMobileStaticHtml(setmeal.getId());

    }

    private void generateMobileStaticHtml(Integer id) {
        //准备模板文件中所需的数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(id);
    }

    /**生成套餐列表静态页面*/
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    /**生成套餐详情静态页面（多个）*/
    public void generateMobileSetmealDetailHtml(Integer id) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal", this.findById(id));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+id+".html",
                    dataMap);

    }

    public void generateHtml(String templateName,String htmlPageName,Map<String, Object> dataMap){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File docFile = new File(outputpath + "\\" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile),"utf-8"));
            // 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 维护中间表
     */
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkitemIds) {
        for (Integer checkitemId : checkitemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", checkitemId);
            map.put("setmeal_id", id);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }

    /**
     * 分页的条件查询--拦截器
     */
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //设置初始条件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2.查询
        Page<Setmeal> page = setmealDao.findPageByQuery(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询所有
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.selectAll();
    }

    /**
     * 根据id查询
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.selectByPrimaryKey(id);
    }

}
