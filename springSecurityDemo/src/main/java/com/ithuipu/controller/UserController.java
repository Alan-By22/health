package com.ithuipu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/29---19:11
 * @描述信息
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        return "userAdd";
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('update')")
    public String update(){
        return "userUpdate";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(){
        return "userDelete";
    }
}
