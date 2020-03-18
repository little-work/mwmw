package com.lilin.dubboserviceprovider.service;

import com.lilin.dubboserviceprovider.mybatis.entity.UserInfo;

import java.util.List;

public interface UserService {

    List<UserInfo> findUser();
}
