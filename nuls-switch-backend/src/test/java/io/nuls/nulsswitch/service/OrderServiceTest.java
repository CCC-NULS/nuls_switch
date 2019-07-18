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
        order.setPrice(new BigDecimal(0.001d));
        order.setTotalNum(1000);
        order.setTotalAmount(new BigDecimal(1));
        order.setTxNum(100);
        orderService.insert(order);
    }

}
