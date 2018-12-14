package com.github.haomao.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @Author:ggq
 * @Date:2018/12/13
 * @Description: Mybatis插件
 */
//定义拦截签名
@Intercepts({
        @Signature(type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class})
})
public class MyPlugin implements Interceptor{

    Properties properties = null;

    //拦截方法逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("插件拦截方法......");
        return invocation.proceed();
    }

    //生成Mybatis拦截器代理对象
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    //设置插件属性
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
