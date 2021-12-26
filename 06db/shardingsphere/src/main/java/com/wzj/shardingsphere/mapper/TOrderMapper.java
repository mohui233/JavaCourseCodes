package com.wzj.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzj.shardingsphere.entity.TOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(TOrder)表数据库访问层
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:32
 */
@Mapper
public interface TOrderMapper extends BaseMapper<TOrder> {

    /**
     * 批量插入数据库
     * @param list
     */
    void batchInsert(@Param("list") List<TOrder> list);
}
