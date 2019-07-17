package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
public class TokenServiceTest {

    @Autowired
    TokenService tokenService;
    @Test
    public void insert() {
        Token token = new Token();
//        token.setTokenName("NULS");
//        token.setTokenSymbol("纳世币");
        token.setTokenName("USDT");
        token.setTokenSymbol("泰达币");
        token.setCreateTime(new Date());
        tokenService.insert(token);
    }

}
