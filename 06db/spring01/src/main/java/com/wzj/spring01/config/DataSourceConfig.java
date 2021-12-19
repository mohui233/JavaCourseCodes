package com.wzj.spring01.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * （必做）读写分离 - 动态切换数据源版本 1.0
 * @author wangzhijie
 */
@Configuration
public class DataSourceConfig {

    // 创建可读数据源
    @Bean(name = "selectDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.select") // application.yml 中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    // 创建可写数据源
    @Bean(name = "updateDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.update") // application.yml 中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

}
