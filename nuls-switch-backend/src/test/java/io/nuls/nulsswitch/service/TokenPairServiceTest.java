package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.entity.TokenPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * <p>
 * 支持的代币单元测试
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NulsSwitchApplication.class)
public class TokenPairServiceTest {

    @Autowired
    TokenPairService tokenPairService;
    @Test
    public void insert() {
        TokenPair tokenPair = new TokenPair();
        tokenPair.setFromTokenId(1);
//        tokenPair.setToTokenId(2);
        //tokenPair.setToTokenId(3);
        tokenPair.setToTokenId(4);
        tokenPairService.insert(tokenPair);
    }

}
