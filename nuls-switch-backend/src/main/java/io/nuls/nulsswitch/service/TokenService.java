package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Token;

import java.util.Map;

/**
 * <p>
 * 支持交易的代币 服务类
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-16
 */
public interface TokenService extends IService<Token> {

    /**
     * 查询所有代币类型
     *
     * @return
     */
    Map<Integer, String> getTokenMap();
}
