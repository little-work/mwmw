package com.lilin.mwmw.DynamicDatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1) // 该切面应当先于 @Transactional 执行
@Component
@Slf4j
public class DynamicDataSourceAspect {
    /**
     * 切换数据源
     * @param point
     * @param targetDataSource
     */
    @Before("@annotation(targetDataSource))")
    public void switchDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        if (!DynamicDataSourceContextHolder.containDataSourceKey(targetDataSource.value())) {
            log.info("DataSource [{}] doesn't exist, use default DataSource [{}] " + targetDataSource.value());
        } else {
            // 切换数据源
            DynamicDataSourceContextHolder.setDataSourceKey(targetDataSource.value());
            log.info("Switch DataSource to [" + DynamicDataSourceContextHolder.getDataSourceKey()
                    + "] in Method [" + point.getSignature() + "]");
        }
    }

    /**
     * 重置数据源
     * @param point
     * @param targetDataSource
     */
    @After("@annotation(targetDataSource))")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        // 将数据源置为默认数据源
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.info("Restore DataSource to [" + DynamicDataSourceContextHolder.getDataSourceKey()
                + "] in Method [" + point.getSignature() + "]");
    }
}
