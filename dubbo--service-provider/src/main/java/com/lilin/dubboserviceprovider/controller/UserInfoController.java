package com.lilin.dubboserviceprovider.controller;


import com.lilin.dubboserviceprovider.Redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserInfoController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${server.port}")
    private String port;


//    @RequestMapping(value = "fingUser" ,method = RequestMethod.GET)
//    public String findUser() {
//        return userService.findUser().get(0).getAddress();
//    }


    /**
     * Redis缓存击穿和Redis分布式锁
     * @return
     */
    @RequestMapping(value = "getRedisValue" ,method = RequestMethod.GET)
    public String getRedisValueByKey() {
        /*for(int i=0;i<5;i++){
            new Thread(()->{
                redisUtils.getValueMoreThread("username");
            }).start();
        }*/


        return redisUtils.getValueMoreThread("username");
    }

    /**
     * 集群环境下使用Redis做session共享
     * @param request
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/setsession")
    public String setSeesion(HttpServletRequest request, String key, String value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return "server port :" + port + "---- value :" + value;
    }

    @RequestMapping("/getsession")
    public String getSeesion(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        String value = (String) session.getAttribute(key);
        return "server port :" + port + "---- value :" + value;
    }





}
