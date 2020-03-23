package com.lilin.dubboserviceprovider.controller;



import com.lilin.dubboserviceprovider.Redis.RedisUtils;
import com.lilin.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    //@Autowired
    //private UserService userService;
    @Autowired
    private RedisUtils redisUtils;


   /* @RequestMapping(value = "fingUser" ,method = RequestMethod.GET)
    public String findUser() {
        return userService.findUser().get(0).getAddress();
    }*/

    @RequestMapping(value = "getRedisValue" ,method = RequestMethod.GET)
    public String getRedisValueByKey() {
        return redisUtils.getJedis().get("mykey");
    }

}
