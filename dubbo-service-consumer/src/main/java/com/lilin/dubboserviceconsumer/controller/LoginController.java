package com.lilin.dubboserviceconsumer.controller;

import com.lilin.interfaces.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping("/authLogin")
    public String authLogin(String userName,String password){
        System.out.println(userName+password);
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                userName,
                password
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            //subject.checkRole("admin");
            //subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }


    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/index")
    public String index() {
        return "index!";
    }



    @RequestMapping("/login")
    public String login() {
        return loginService.getName();
    }


    /**
     * 同步返回
     *
     * @return
     */
    @RequestMapping("/login2")
    public String login2() {
        loginService.testSync();
        return "服务器执行完返回结果-task";

    }

    /**
     * 异步返回
     *
     * @return
     */
    @RequestMapping("/login3")
    public String login3() {
        loginService.testAsync();
        return "服务器执行完返回结果-task";

    }

    /**
     * @return
     * @Async异步测试
     */
    @RequestMapping("/login4")
    public String login4() {
        //loginService.task();
        return "服务器执行完返回结果-task";

    }

}
