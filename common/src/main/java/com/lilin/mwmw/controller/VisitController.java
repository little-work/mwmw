package com.lilin.mwmw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VisitController {


    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String wefew() {
        return "index";
    }


    //测试一次记得删除
    public String getThisBean() {
        return "通过Filter的方式将这个Bean注册到容器中，并成功调用这个方法";
    }

}
