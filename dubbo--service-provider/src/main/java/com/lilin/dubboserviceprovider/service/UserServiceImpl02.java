package com.lilin.dubboserviceprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lilin.dubboserviceprovider.mybatis.mapper.UserDao;
import com.lilin.interfaces.UserService;
import com.lilin.interfaces.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


//这个service的作用就是暴露服务，第二个版本
@Service(version = "2.0.0", interfaceClass = UserService.class)
public class UserServiceImpl02 implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public List<UserInfo> findUser() {
        List<UserInfo> list=new ArrayList<>();
        UserInfo userInfo=new UserInfo();
        userInfo.setUsername("李林2");
        list.add(userInfo);
        return list;
        //return userDao.findUser();
    }
}
