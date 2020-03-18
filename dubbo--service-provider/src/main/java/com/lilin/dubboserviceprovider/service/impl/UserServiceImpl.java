package com.lilin.dubboserviceprovider.service.impl;

import com.lilin.dubboserviceprovider.mybatis.entity.UserInfo;
import com.lilin.dubboserviceprovider.mybatis.mapper.UserDao;
import com.lilin.dubboserviceprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public List<UserInfo> findUser() {
        return userDao.findUser();
    }
}
