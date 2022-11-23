package com.ithuipu.controller;

import com.ithuipu.constant.MessageConstant;
import com.ithuipu.constant.RedisMessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.utils.SMSUtils;
import com.ithuipu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/23---16:15
 * @描述信息
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    /**
     * 发送验证码---时效性
     * 注入jedis--redis的java客户端
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * 体检预约时发送的验证码
     * send4Order.do?telephone
     */
    @RequestMapping("/send4Order")
    public Result sendCode(String telephone) {
        /**1.获得验证码*/
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        /**2.短信发送*/
        try {
            SMSUtils.sendShortMessage(SMSUtils.TEMPLATE_SEND_ID, telephone, code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        /**3.获取验证码*/
        System.out.println(code + "-----------------");
        /**4.将验证码存入redis--设置过期时间*/
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, code.toString());

        /**5.发送成功*/
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
