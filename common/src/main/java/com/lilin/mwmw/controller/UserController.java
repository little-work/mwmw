package com.lilin.mwmw.controller;


import com.lilin.mwmw.dynamicDatasource.TargetDataSource;
import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {



    private UserServiceImpl userServiceImpl;

    @Autowired
    public  UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl=userServiceImpl;
    }


    @TargetDataSource(value="test")
    @RequestMapping(value = "insertUser",method = RequestMethod.GET)
    public String saveUser(){
        User user =new User();
        user.setUsername("lilin");
        user.setAge(28);
        user.setPassword("123456");
        user.setUserType(2);
        user.setCreateTime(new Date());
        user.setLastUpdateTime(new Date());
        userServiceImpl.manualTransactionManager(user);
        return "mwmw中插入用户成功";
    }

    @TargetDataSource(value="prod")
    @RequestMapping(value = "insertUser2",method = RequestMethod.GET)
    public String saveUser2(){
        User user =new User();
        user.setUsername("yejiamei");
        user.setPassword("7654321");
        user.setAge(28);
        user.setUserType(1);
        user.setCreateTime(new Date());
        user.setLastUpdateTime(new Date());
        userServiceImpl.manualTransactionManager(user);
        return "world中插入用户成功";
    }

}
