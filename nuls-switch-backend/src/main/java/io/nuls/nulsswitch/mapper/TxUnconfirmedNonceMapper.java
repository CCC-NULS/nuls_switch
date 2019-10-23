package io.nuls.nulsswitch.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.entity.TxUnconfirmedNonce;

/**
 * <p>
 * 本地未确认交易nonce Mapper 接口
 * </p>
 *
 * @author qinyifeng
 * @since 2019-10-21
 */
public interface TxUnconfirmedNonceMapper extends BaseMapper<TxUnconfirmedNonce> {

    /**
     * 删除该订单所有未确认交易nonce
     *
     * @param orderId 订单号
     */
    void deleteNonceByOrderId(String orderId);
}
