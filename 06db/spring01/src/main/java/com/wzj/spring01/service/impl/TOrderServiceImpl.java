package com.wzj.spring01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzj.spring01.service.TOrderService;
import com.wzj.spring01.entity.TOrder;
import com.wzj.spring01.mapper.TOrderMapper;
import com.wzj.spring01.task.BatchInsertCallable;
import com.wzj.spring01.util.SnowflakeIdWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 订单表(TOrder)表服务实现类
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:31
 */
@Service
@AllArgsConstructor
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    private final TOrderMapper tOrderMapper;

    /**
     * 批量新增订单
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchInsert() {
        // 生成订单号
        List<TOrder> listOrder = new ArrayList<>();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000000; i++) {
            long orderNum = idWorker.nextId();
            listOrder.add(new TOrder().setOrderNum(orderNum));
        }
        // 订单批量入库
        batchInsert(listOrder);
        return true;
    }

    /**
     * 利用线程池实现多线程批量插入订单
     */
    public boolean batchInsert(List<TOrder> listOrder) {
        // 创建自适应机器本身线程数量的线程池
        Integer process = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                process,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        Future<Boolean> future = null;
        try {
            future = executorService.submit(new BatchInsertCallable(listOrder, tOrderMapper));
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return future.get();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }
}
