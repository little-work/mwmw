package com.lilin.mwmw.dynamicDatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 1、定义DynamicDataSource类继承抽象类AbstractRoutingDataSource，
     * 并实现了determineCurrentLookupKey()方法。
     *
     * 2、把配置的多个数据源会放在AbstractRoutingDataSource的
     * targetDataSources和defaultTargetDataSource中，
     * 然后通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。
     *
     * 3、调用AbstractRoutingDataSource的getConnection()的方法的时候，
     * 先调用determineTargetDataSource()方法返回DataSource在进行getConnection()。
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    /**
     * 父类方法-设置默认数据源
     * @param defaultDataSource
     */
    public void setDefaultDataSource(Object defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }
    /**
     * 设置数据源
     * @param dataSources
     */
    public void setDataSources(Map<Object, Object> dataSources) {
        super.setTargetDataSources(dataSources);
        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.addDataSourceKeys(dataSources.keySet());
    }
}
