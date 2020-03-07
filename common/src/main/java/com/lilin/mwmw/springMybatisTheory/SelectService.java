package com.lilin.mwmw.springMybatisTheory;

import com.lilin.mwmw.springMybatisTheory.mappers.SelectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectService {

    @Autowired
    private SelectDao selectDao;


    public void query(){
        selectDao.query();
    }
}
