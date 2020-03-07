package com.lilin.mwmw.springMybatisTheory.mappers;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectDao {


    @Select("select * from user")
    void query();
}
