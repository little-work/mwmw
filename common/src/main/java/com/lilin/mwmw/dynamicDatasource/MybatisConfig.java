package com.lilin.mwmw.dynamicDatasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MybatisConfig {

    @Bean("prod")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.prod")
    public DataSource prod() {
        return DataSourceBuilder.create().build();
    }

    @Bean("test")
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSource test() {
        return DataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("prod", prod());
        dataSourceMap.put("test", test());
        // 将 prod 数据源作为默认指定的数据源
        dynamicDataSource.setDefaultDataSource(prod());
        // 将 prod 和 test 数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        /**
         * 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
         *
         * mybatis在执行sql语句前调用获取数据库连接的时候，使用这个数据源dynamicDataSource()（实现了DataSource接口）
         * 这个数据源在获取连接的时候，会根据AOP传入的值动态决定拥有那个数据源获取连接
         */
        sessionFactory.setDataSource(dynamicDataSource());
        sessionFactory.setTypeAliasesPackage("com.lilin.mwmw.bo");    // 扫描Model
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));    // 扫描映射文件
        return sessionFactory.getObject();
    }

    @Bean(name="tx")
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
