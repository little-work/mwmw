package com.lilin.dubboserviceprovider.service.Impl;

import com.lilin.dubboserviceprovider.mybatis.entity.AppQuartz;
import com.lilin.dubboserviceprovider.mybatis.mapper.QuartzDao;
import com.lilin.dubboserviceprovider.service.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzImpl implements Quartz {

    @Autowired
    private QuartzDao quartzDao;

    @Override
    public void insertAppQuartzSer(AppQuartz appQuartz) {
        quartzDao.insertAppQuartzSer(appQuartz);
    }
}
