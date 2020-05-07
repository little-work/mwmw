package com.lilin.mwmw.springMVC.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Interceptor {


    boolean preHandler(HttpServletRequest req, HttpServletResponse resp);

    boolean postHandler(HttpServletRequest req, HttpServletResponse resp);

    boolean completeHandler(HttpServletRequest req, HttpServletResponse resp);
}
