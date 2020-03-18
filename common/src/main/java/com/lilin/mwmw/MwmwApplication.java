package com.lilin.mwmw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 以禁用数据源默认自动配置
 * 数据源默认自动配置会读取 spring.datasource.* 的属性创建数据源，所以要禁用以进行定制。
 * @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 */
//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableDubbo
@MapperScan("com.lilin.mwmw.dao")   //开启mybatis
@EnableTransactionManagement    // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
public class MwmwApplication //implements TransactionManagementConfigurer
{

    /**
     *  spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager
     *  如果是单个事务 那么下面的操作没必要  一切springboot会帮我们操作
     */
//    @Resource(name="tx1")
//    private PlatformTransactionManager txManager1;

    /**
     * 创建事务管理器1
     *
     * 其中 dataSource 框架会自动为我们注入
     * DataSourceTransactionManager需要TransactionManagementConfigurer的实现类
     * 如果没有这个实现类 那么这个容器会启动失败
     *
     *
     * @param dataSource
     * @return
     */
//    @Bean(name="tx1")
//    public PlatformTransactionManager txManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
    /**
     *
     * 创建事务管理器2
     *
     * 如果Spring容器中存在多个 PlatformTransactionManager 实例，并且没有实现接口
     * TransactionManagementConfigurer 指定默认值，在我们在方法上使用注解 @Transactional 的时候，
     * 就必须要用value指定，如果不指定，则会抛出异常。
     *
     * 对于系统需要提供默认事务管理的情况下，实现接口 TransactionManagementConfigurer 指定。
     *
     * 对有的系统，为了避免不必要的问题，在业务中必须要明确指定 @Transactional 的 value 值的情况下。
     * 不建议实现接口 TransactionManagementConfigurer，这样控制台会明确抛出异常，开发人员就不会忘记主动指定
     */
    /*
    @Bean(name = "tx2")
    public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }*/


    /**
     * 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
     */

//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return txManager1;
//    }

    public static void main(String[] args) {
        SpringApplication.run(MwmwApplication.class, args);
    }

}
