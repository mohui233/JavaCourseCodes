package com.wzj.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单表(TOrder)表实体类
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:31
 */
@Data
@TableName("t_order")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuppressWarnings("serial")
public class TOrder extends Model<TOrder> {

    private Long orderId;
    private Integer userId;
    private String status;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
