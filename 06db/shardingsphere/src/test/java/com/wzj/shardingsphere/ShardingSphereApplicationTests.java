package com.wzj.shardingsphere;

import com.wzj.shardingsphere.entity.TOrder;
import com.wzj.shardingsphere.service.TOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingSphereApplication.class)
public class ShardingSphereApplicationTests {

    @MockBean
    private TOrderService tOrderService;

    @Test
    public void save () {
        Mockito.when(tOrderService.save(Mockito.any(TOrder.class))).thenReturn(true);
        TOrder tOrder = new TOrder();
        tOrder.setUserId(1).setStatus("OK");
        System.out.println(tOrderService.save(tOrder)==true?"插入成功":"插入失败");
    }

}
