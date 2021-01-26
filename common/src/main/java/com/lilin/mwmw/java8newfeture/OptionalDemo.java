package com.lilin.mwmw.java8newfeture;

import com.lilin.mwmw.bo.Login;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-07-02 20:09
 */
public class OptionalDemo {


    public static void main(String[] args) {

        Login login1 = null;
        Login login2 = new Login("wqe", "eq", "rew",null);

        Optional<Login> opt = Optional.ofNullable(login1);

        System.out.println(opt.isPresent());

        opt.ifPresent(t-> System.out.println(t.getPassword()));

        //Login loginOpt = Optional.ofNullable(login1).orElse(login2);
        //System.out.println(loginOpt.getPassword());


    }
}
