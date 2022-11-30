package com.ithuipu.controller;

import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/30---18:27
 * @描述信息
 */

@RestController
@RequestMapping("/user")
public class UserController {

    /**认证成功的时候会,保存我们的信息*/
    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(userDetails);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,userDetails.getUsername());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
