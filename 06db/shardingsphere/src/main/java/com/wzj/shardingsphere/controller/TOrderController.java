package com.wzj.shardingsphere.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzj.shardingsphere.entity.TOrder;
import com.wzj.shardingsphere.service.TOrderService;
import com.wzj.shardingsphere.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单表(TOrder)表控制层
 *
 * @author wangzhijie
 * @since 2021-12-19 12:16:32
 */
@RestController
@RequestMapping("tOrder")
@RequiredArgsConstructor
public class TOrderController {
    /**
     * 服务对象
     */
    private final TOrderService tOrderService;


    /**
     * 分页查询所有订单
     *
     * @param page   分页对象
     * @param tOrder 查询实体
     * @return 分页订单
     */
    @GetMapping("/page")
    public R getTOrderPage(Page<TOrder> page, TOrder tOrder) {
        return R.ok(this.tOrderService.page(page, new QueryWrapper<>(tOrder)));
    }


    /**
     * 通过orderId查询订单表
     *
     * @param orderId
     * @return 单条订单
     */
    @GetMapping("{orderId}")
    public R getById(@PathVariable Long orderId) {
        return R.ok(this.tOrderService.getByOrderId(orderId));
    }


    /**
     * 新增订单
     *
     * @param tOrder 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody TOrder tOrder) {
        return R.ok(tOrderService.save(tOrder));
    }


    /**
     * 修改订单表
     *
     * @param tOrder 订单表
     * @return R
     */
    @PutMapping
    public R updateById(@RequestBody TOrder tOrder) {
        return R.ok(this.tOrderService.updateById(tOrder));
    }


    /**
     * 通过id删除订单表
     *
     * @param orderId
     * @return R
     */
    @DeleteMapping("/{orderId}")
    public R removeByOrderId(@PathVariable Long orderId) {
        return R.ok(this.tOrderService.removeByOrderId(orderId));
    }

}
