package com.lilin.mwmw.aopChain;

import com.lilin.mwmw.aopTheory.MyMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 切面类
 */
public class TransactionManager {


    public void start() {
        System.out.println("start tx");
    }

    public void commit() {
        System.out.println("commit tx");
    }

    public void rollback() {
        System.out.println("rollback tx");
    }
}
