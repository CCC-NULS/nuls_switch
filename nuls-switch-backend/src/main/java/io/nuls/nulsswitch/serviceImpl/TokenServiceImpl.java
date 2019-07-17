package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.mapper.TokenMapper;
import io.nuls.nulsswitch.service.TokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支持交易的代币 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

}
