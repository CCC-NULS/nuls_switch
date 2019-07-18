package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.entity.TokenPair;
import io.nuls.nulsswitch.mapper.TokenPairMapper;
import io.nuls.nulsswitch.service.TokenPairService;
import io.nuls.nulsswitch.web.vo.token.TokenPairVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    TokenPairMapper tokenPairMapper;

    @Override
    public List<TokenPairVO> queryTokenPairList() {
        return this.tokenPairMapper.queryTokenPairList();
    }

    @Override
    public List<TokenPair> selectAll() {
        return this.tokenPairMapper.selectAll();
    }
}
