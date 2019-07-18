package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.entity.TokenPair;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.web.vo.token.TokenPairVO;

import java.util.List;

/**
 * <p>
 * 代币兑换交易对 服务类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TokenPairService extends IService<TokenPair> {

    List<TokenPairVO> queryTokenPairList();

    List<TokenPair> selectAll();

}
