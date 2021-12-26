package com.wzj.shardingsphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzj.shardingsphere.entity.TOrder;

import java.io.Serializable;

/**
 * 订单表(TOrder)表服务接口
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:31
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 根据订单ID查询订单
     * @param userId
     * @return
     */
    TOrder getByOrderId(Long userId);

    Integer removeByOrderId(Long orderId);
}
