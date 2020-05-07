package com.lilin.mwmw.springMVC.hander;

import com.lilin.mwmw.springMVC.interceptors.Interceptor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
public class HandlerExecutionChain {


    private Object handler;

    private Interceptor interceptor;



    public boolean applyPreHandle (HttpServletRequest req, HttpServletResponse resp){
        return this.interceptor.preHandler(req,resp);
    }

}
