package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NulsSwitchApplication.class)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;


    @Test
    public void insert() {
        Order order = new Order();
        order.setOrderId(System.currentTimeMillis() + "");
        order.setAddress("my");
        //order.setAddress("tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG");
        //order.setAddress("tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD");
        order.setTxType(1);
        order.setStatus(2);
        order.setFromTokenId(1);
        order.setToTokenId(3);
        order.setPrice(10000000L);
        order.setTotalNum(101000000000L);
        order.setTotalAmount(100000000L);
        order.setTxNum(1000000000L);
        orderService.insert(order);
    }

}
