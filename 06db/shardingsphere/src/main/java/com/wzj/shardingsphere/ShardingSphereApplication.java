package com.wzj.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * （必做一）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表，并在新结构在演示常见的增删改查操作
 * @author wangzhijie
 */
@SpringBootApplication(scanBasePackages = "com.wzj.shardingsphere")
@MapperScan("com.wzj.shardingsphere.mapper")
public class ShardingSphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingSphereApplication.class, args);

	}

}
