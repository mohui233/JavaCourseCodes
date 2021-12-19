package com.wzj.spring01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzj.spring01.entity.TOrder;

/**
 * 订单表(TOrder)表服务接口
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:31
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 批量新增订单
     * @return
     */
    boolean batchInsert() throws InterruptedException;
}
