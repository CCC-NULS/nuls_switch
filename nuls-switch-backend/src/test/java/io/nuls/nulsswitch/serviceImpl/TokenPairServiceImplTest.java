package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.service.TokenPairService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NulsSwitchApplication.class)
public class TokenPairServiceImplTest {
    @Autowired
    TokenPairService tokenPairService;

    @Test
    public void test(){

    }



}