package com.lilin.mwmw.DynamicDatasource;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 * @author Louis
 * @date Oct 31, 2018
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    /**
     * 数据源key值
     * @return
     */
    String value();
}
