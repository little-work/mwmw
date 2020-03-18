package com.lilin.dubboserviceprovider.mybatis.mapper;

import com.lilin.dubboserviceprovider.mybatis.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    List<UserInfo> findUser();
}
