package com.lilin.mwmw.controller;


import com.lilin.mwmw.DynamicDatasource.DataSource;
import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserServiceImpl userService;


    @DataSource(value="test")
    @RequestMapping(value = "insertUser",method = RequestMethod.GET)
    public String saveUser(){
        User user =new User();
        user.setUsername("yejiamei");
        user.setPassword("7654321");
        user.setUserType(2);
        userService.manualTransactionManager(user);
        return "插入用户成功";
    }

}
