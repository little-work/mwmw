package com.lilin.mwmw.service.impl;

import com.lilin.mwmw.bo.User;
import com.lilin.mwmw.mapper.UserMapper;
import com.lilin.mwmw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {


    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    //事务管理器
    @Resource(name="tx")
    PlatformTransactionManager transactionManager;


    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public void insertUser(User user) {
        userMapper.addUser(user);
        //int i=10/0;
    }


    /**
     * 如果需要try catch 捕获异常的时候  必须使用手动回滚事务
     */
    public void manualTransactionManager(User user) {
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("manualTransation");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //手动开启事务
        TransactionStatus status=transactionManager.getTransaction(def);

        try {

            //业务逻辑所在  对数据库进行增删改
            userMapper.addUser(user);
            //int i=10/0;

            //提交事务
            transactionManager.commit(status);
        }catch (Exception e){
            //回滚事务
            transactionManager.rollback(status);
            throw e;
        }
    }
}
