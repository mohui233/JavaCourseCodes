package com.wzj.spring01.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 雪花算法
 * @author wangzhijie
 */
@Slf4j
@Component
public class IdGeneratorSnowflake {

    // 第几号机房
    private long workerId = 0;

    // 第几号机器
    private long datacenterId = 1;

    private Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    // 构造后开始执行，加载初始化工作
    @PostConstruct
    public void init(){
        try{
            // 获取本机的ip地址编码
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId: " + workerId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败 ----> " + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    // 测试
    public static void main(String[] args) {
        long orderNum = new IdGeneratorSnowflake().snowflakeId(0,1);
        System.out.println(orderNum);
    }
}
