/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wzj.spring01.util;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置 Hikari 连接池
 * @author wangzhijie
 */
public final class HikariDataUtil {

    private static String driverClassName;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 通过静态代码块，初始化数据库连接配置数据，并且注册数据库驱动
     */
    static {
        try {
            Properties pr = new Properties();
            pr.load(new FileInputStream(org.springframework.util.ResourceUtils.getFile("classpath:application.yml")));
            driverClassName = pr.getProperty("driver-class-name");
            url = pr.getProperty("url");
            username = pr.getProperty("username");
            password = pr.getProperty("password");
            Class.forName(driverClassName);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
    }

    public static DataSource createDataSource() {
        HikariDataSource result = new HikariDataSource();
        result.setDriverClassName(driverClassName);
        result.setJdbcUrl(url);
        result.setUsername(username);
        result.setPassword(password);
        return result;
    }
}
