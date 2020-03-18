package com.lilin.mwmw.mapper;

import com.lilin.mwmw.bo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {


    List<User> getAllUser();

    Integer addUser(User user);

    Integer updateUserById(User user);

    Integer deleteUserById(Integer id);
}
