package io.nuls.nulsswitch.serviceImpl;

import io.nuls.core.basic.Result;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.mapper.TradeMapper;
import io.nuls.nulsswitch.service.TradeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.web.exception.NulsRuntimeException;
import io.nuls.v2.util.NulsSDKTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
@Slf4j
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements TradeService {


    /**
     * 将数据广播到区块链
     * 更新交易状态
     */
    @Override
    public String broadcast(Trade trade, String txHex) {
        String hash = null;

        Result result = NulsSDKTool.broadcast(txHex);
        Map map = (Map) result.getData();
        hash = (String) map.get("hash");
        if (hash == null) {
            throw new NulsRuntimeException(CommonErrorCode.BROADCAST_ERROR);
        }
        trade.setStatus(SwitchConstant.TX_TRADE_STATUS_CONFIRMING);
        trade.setTxHash(hash);
        updateById(trade);

        return hash;
    }
}
