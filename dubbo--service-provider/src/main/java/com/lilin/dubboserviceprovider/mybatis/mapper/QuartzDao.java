package com.lilin.dubboserviceprovider.mybatis.mapper;

import com.lilin.dubboserviceprovider.mybatis.entity.AppQuartz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuartzDao {

    void insertAppQuartzSer(@Param("aq") AppQuartz appQuartz);
}
