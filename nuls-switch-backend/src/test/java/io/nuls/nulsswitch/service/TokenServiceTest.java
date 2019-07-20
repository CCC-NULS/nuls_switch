package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Token;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
        List<Token> tokenList= Lists.newArrayList();
        Token token = new Token();
        token.setTokenId(1);
        token.setCreateTime(new Date());
        token.setTokenSymbol("NULS");
        token.setTokenName("纳世币");
        tokenList.add(token);

        token = new Token();
        token.setTokenId(2);
        token.setCreateTime(new Date());
        token.setTokenSymbol("USDT");
        token.setTokenName("泰达币");
        tokenList.add(token);

        token = new Token();
        token.setTokenId(3);
        token.setCreateTime(new Date());
        token.setTokenSymbol("ETH");
        token.setTokenName("以太坊");
        tokenList.add(token);

        token = new Token();
        token.setTokenId(4);
        token.setCreateTime(new Date());
        token.setTokenSymbol("BTC");
        token.setTokenName("比特币");
        tokenList.add(token);

        token = new Token();
        token.setTokenId(5);
        token.setCreateTime(new Date());
        token.setTokenSymbol("EOS");
        token.setTokenName("柚子币");
        tokenList.add(token);
        tokenService.insertBatch(tokenList);
    }

}
