package com.wzj.spring01;

import com.wzj.spring01.entity.TOrder;
import com.wzj.spring01.service.TOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring01Application.class)
public class Spring01ApplicationTests {

    @MockBean
    private TOrderService tOrderService;

    @Test
    public void save () throws InterruptedException {
        Mockito.when(tOrderService.save(Mockito.any(TOrder.class))).thenReturn(true);
        TOrder tOrder = new TOrder();
        tOrder.setUserId(1L).setOrderTotalPrice(2.0);
        System.out.println(tOrderService.save(tOrder)==true?"插入成功":"插入失败");
        Mockito.when(tOrderService.batchInsert()).thenReturn(true);
        System.out.println(tOrderService.batchInsert()==true?"批量插入成功":"批量插入失败");
    }

}
