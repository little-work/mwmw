package com.lilin.interfaces;


import com.lilin.interfaces.entity.UserInfo;

import java.util.List;

public interface UserService {

    List<UserInfo> findUser();
}
