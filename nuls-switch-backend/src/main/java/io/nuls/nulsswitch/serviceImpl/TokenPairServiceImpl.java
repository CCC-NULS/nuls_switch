package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.entity.TokenPair;
import io.nuls.nulsswitch.mapper.TokenPairMapper;
import io.nuls.nulsswitch.service.TokenPairService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代币兑换交易对 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
public class TokenPairServiceImpl extends ServiceImpl<TokenPairMapper, TokenPair> implements TokenPairService {

}
