package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.constant.RedisMessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.pojo.Member;
import com.ithuipu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.Date;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/28---16:34
 * @描述信息 会员
 */

@RestController
@RequestMapping("/member")
public class MemberController {


    /**
     * 注入service--添加
     * 验证码--redis
     */
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 手机快速登陆
     */
    @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletResponse response) {
        //验证码的校验
        String telephone = (String) map.get("telephone");
        //从jedis中取出来
        String code_redis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //获取前台传入的code
        Spring validateCode = (Spring) map.get("validateCode");
        //判断
        if (code_redis == null || !code_redis.equals(validateCode)) {
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        /**验证码正确,判断用户是否是会员*/
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            //注册为会员
            memberService.add(member);
        }

        /**登陆成功
         * 1.将用户的手机--存入cookie--身份的识别
         */
        Cookie cookie = new Cookie("login_telepthone", telephone);
        cookie.setPath("/");//携带的路径
        cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
        response.addCookie(cookie);

        /**2.保存用户的信息--session(分布式session不共享)redis
         * 用户信息保存到redis,保存30分钟-存json*/
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(member);
            jedisPool.getResource().setex(telephone, 60 * 30, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
