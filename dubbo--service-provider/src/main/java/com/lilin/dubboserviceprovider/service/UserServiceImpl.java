package com.lilin.dubboserviceprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lilin.dubboserviceprovider.mybatis.mapper.UserDao;
import com.lilin.interfaces.UserService;
import com.lilin.interfaces.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


//这个service的作用就是暴露服务  首次版本
@Service(version = "1.0.0", interfaceClass = UserService.class,timeout = 1000)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public List<UserInfo> findUser() {
        /**
         * 模拟错误
         */
//        if(Math.random()>0.5){
//            throw new RuntimeException();
//        }

        return userDao.findUser();
    }
}
