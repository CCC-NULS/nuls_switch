package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.mapper.TokenMapper;
import io.nuls.nulsswitch.service.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 支持交易的代币 服务实现类
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-16
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    @Resource
    TokenMapper tokenMapper;

    @Override
    public Map<Integer, String> getTokenMap() {
        // 查询所有token
        Map<Integer, String> tokenMap = Optional.ofNullable(tokenMapper.selectList(null)).orElse(Collections.emptyList()).stream().collect(Collectors.toMap(Token::getTokenId, Token::getTokenSymbol));
        return tokenMap;
    }
}
