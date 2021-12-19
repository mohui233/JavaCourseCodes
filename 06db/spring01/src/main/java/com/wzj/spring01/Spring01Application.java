package com.wzj.spring01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangzhijie
 */
@SpringBootApplication(scanBasePackages = "com.wzj.spring01")
@MapperScan("com.wzj.spring01.mapper")
public class Spring01Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring01Application.class, args);

	}

}
