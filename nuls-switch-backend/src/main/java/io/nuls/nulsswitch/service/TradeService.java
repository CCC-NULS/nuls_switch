package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.entity.Trade;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.web.dto.order.QueryTradeReqDto;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 服务类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TradeService extends IService<Trade> {
    Page<Trade> queryTradeByOrderIdPage(QueryTradeReqDto reqDto);

    String broadcast(Trade trade, String txHex);
}
