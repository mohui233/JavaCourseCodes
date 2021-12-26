package com.wzj.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzj.shardingsphere.service.TOrderService;
import com.wzj.shardingsphere.entity.TOrder;
import com.wzj.shardingsphere.mapper.TOrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单表(TOrder)表服务实现类
 *
 * （必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:31
 */
@Service
@AllArgsConstructor
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    private final TOrderMapper tOrderMapper;

    @Override
    public TOrder getByOrderId(Long orderId) {
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("order_id", orderId);
        return this.getOne(entityWrapper);
    }

    @Override
    public Integer removeByOrderId(Long orderId) {
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("order_id", orderId);
        return tOrderMapper.delete(entityWrapper);
    }
}
