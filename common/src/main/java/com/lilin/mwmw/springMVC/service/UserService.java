package com.lilin.mwmw.springMVC.service;

import java.util.Map;

public interface UserService {

    int insert(Map map);

    int delete(Map map);

    int update(Map map);

    int select(Map map);

}
