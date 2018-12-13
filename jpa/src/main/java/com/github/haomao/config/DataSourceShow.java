package com.github.haomao.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @Author:ggq
 * @Date:2018/12/10
 * @Description:监测数据库连接池类型
 */
@Component
public class DataSourceShow implements ApplicationContextAware{

    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println("--------------------------");
        System.out.println("数据库连接池类型------>"+dataSource.getClass().getName());
        System.out.println("--------------------------");
    }
}
