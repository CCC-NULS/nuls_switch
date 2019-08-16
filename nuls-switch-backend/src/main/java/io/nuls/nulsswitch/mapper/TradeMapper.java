package io.nuls.nulsswitch.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuls.nulsswitch.entity.Trade;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 Mapper 接口
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TradeMapper extends BaseMapper<Trade> {

    /**
     * 取消订单交易
     *
     * @param orderId 订单ID
     * @param tradeId 交易ID
     */
    void cancelOrderTrade(@Param("orderId") String orderId, @Param("tradeId") String tradeId);
}
