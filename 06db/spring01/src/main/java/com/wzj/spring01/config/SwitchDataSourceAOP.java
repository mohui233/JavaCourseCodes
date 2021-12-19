package com.wzj.spring01.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wangzhijie
 */
@Aspect
@Component
@Lazy(false)
@Order(0) // Order设定AOP执行顺序 使之在数据库事务上先执行
public class SwitchDataSourceAOP {
    // 这里切到你的方法目录
    @Before("execution(* com.wzj.spring01.service.*.*(..))")
    public void process(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        // 如果方法以这些命名开头就使用查询数据源，反之使用修改数据源
        if (methodName.startsWith("get") || methodName.startsWith("count") || methodName.startsWith("find")
                || methodName.startsWith("list") || methodName.startsWith("select") || methodName.startsWith("check")) {
            DataSourceContextHolder.setDbType("selectDataSource");
        } else {
            // 切换dataSource
            DataSourceContextHolder.setDbType("updateDataSource");
        }
    }
}
