package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.NulsSwitchApplication;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.entity.TokenPair;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class TokenPairServiceTest {

    @Autowired
    TokenPairService tokenPairService;
    @Test
    public void insert() {
        List<TokenPair> tokenPairList= Lists.newArrayList();
        TokenPair tokenPair = new TokenPair();
        tokenPair.setFromTokenId(1);
        tokenPair.setToTokenId(2);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(1);
        tokenPair.setToTokenId(3);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(1);
        tokenPair.setToTokenId(4);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(1);
        tokenPair.setToTokenId(5);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(2);
        tokenPair.setToTokenId(1);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(2);
        tokenPair.setToTokenId(3);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(2);
        tokenPair.setToTokenId(4);
        tokenPairList.add(tokenPair);

        tokenPair = new TokenPair();
        tokenPair.setFromTokenId(2);
        tokenPair.setToTokenId(5);
        tokenPairList.add(tokenPair);
        tokenPairService.insertBatch(tokenPairList);
    }

}
