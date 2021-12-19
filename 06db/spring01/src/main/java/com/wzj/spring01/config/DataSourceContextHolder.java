package com.wzj.spring01.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author wangzhijie
 */
@Component
@Lazy(false)
public class DataSourceContextHolder {
    // 采用ThreadLocal 保存本地多数据源（selectDataSource或updateDataSource）
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源类型
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

}
