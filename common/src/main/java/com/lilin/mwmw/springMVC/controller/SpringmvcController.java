package com.lilin.mwmw.springMVC.controller;

import com.lilin.mwmw.springMVC.annotation.MyController;
import com.lilin.mwmw.springMVC.annotation.MyQuatifier;
import com.lilin.mwmw.springMVC.annotation.MyRequestMapping;
import com.lilin.mwmw.springMVC.service.SpringmvcService;
import com.lilin.mwmw.springMVC.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController("find")
public class SpringmvcController {

    @MyQuatifier("myServiceImpl")
    private UserService myService;

    @MyQuatifier("springmvcServiceImpl")
    private SpringmvcService smService;


    @MyRequestMapping("insert")
    public String insert(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.insert(null);
        smService.insert(null);
        return "ee";
    }
    @MyRequestMapping("update")
    public String update(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.update(null);
        smService.update(null);
        return null;
    }

    @MyRequestMapping("select")
    public String select(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.select(null);
        smService.select(null);
        return null;
    }
}
