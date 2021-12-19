package com.wzj.spring01.task;

import com.wzj.spring01.entity.TOrder;
import com.wzj.spring01.mapper.TOrderMapper;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangzhijie
 */
public class BatchInsertCallable implements Callable<Boolean> {

    private static Integer batchCount = 2500;

    private List<TOrder> listOrder;

    private TOrderMapper tOrderMapper;

    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2,
                    Runtime.getRuntime().availableProcessors(),
                    2L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(100),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );

    public BatchInsertCallable(List<TOrder> listOrder, TOrderMapper tOrderMapper) {
        this.listOrder = listOrder;
        this.tOrderMapper = tOrderMapper;
    }

    @Override
    public Boolean call(){
        try {
            batchOp(listOrder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void batchOp(List<TOrder> list) {
        if(!list.isEmpty()){
            Integer size = list.size();
            if(size<=batchCount){
                tOrderMapper.batchInsert(list);
            }else if(size>batchCount){
                batchOpSplit(list,batchCount);
            }
        }
    }

    /**
     * 切割
     * @param list
     * @param batchCount
     */
    private void batchOpSplit(List<TOrder> list, int batchCount) {
        int limit = countStep(list.size());
        // Java8 Stream 大数据量List分批处理
        List<List<TOrder>> splitList = Stream.iterate(0, n -> n + 1).limit(limit).parallel().map(a -> list.stream().skip(a * batchCount).limit(batchCount).parallel().collect(Collectors.toList())).collect(Collectors.toList());
        try {
            for(List<TOrder> listTOrder : splitList){
                // 再调batchOp方法，这里的多线程是多个小集合往数据库插
                threadPoolExecutor.execute(()->{
                    batchOp(listTOrder);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPoolExecutor.shutdown();
        }
    }

    /**
     * 按每2500个一组分割，计算切分次数
     */
    private static Integer countStep(Integer size) {
        return (size + batchCount - 1) / batchCount;
    }
}
