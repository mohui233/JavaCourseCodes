package com.wzj.spring01.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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

    /**
     * 订单号码
     */
    private Long orderNum;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单总价
     */
    private Double orderTotalPrice;

    /**
     * 订单状态 0 未付款 1 已付款 2 已取消 3 已退款
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志 0：正常 1：已删除
     */
    private String delFlag;

}
