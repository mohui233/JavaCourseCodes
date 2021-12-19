package com.wzj.spring01.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzj.spring01.entity.TOrder;
import com.wzj.spring01.service.TOrderService;
import com.wzj.spring01.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;

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
     * 批量新增订单
     *
     * @return 新增结果
     */
    @GetMapping("/batchInsert")
    public R batchInsert() throws InterruptedException {
        return R.ok(tOrderService.batchInsert());
    }


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
     * 通过id查询订单表
     *
     * @param id 主键
     * @return 单条订单
     */
    @GetMapping("{id}")
    public R getById(@PathVariable Serializable id) {
        return R.ok(this.tOrderService.getById(id));
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
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Serializable id) {
        return R.ok(this.tOrderService.removeById(id));
    }

}
