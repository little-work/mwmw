package com.lilin.mwmw.jdkProxy;

public class UserServiceImpl implements UserService {
    @Override
    public void getName() {
        System.out.println("lilin");
    }

    @Override
    public void getAge() {
        System.out.println(28);
    }
}
