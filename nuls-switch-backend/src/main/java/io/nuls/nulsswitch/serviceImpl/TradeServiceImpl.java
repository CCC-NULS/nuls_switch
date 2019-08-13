package io.nuls.nulsswitch.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.nuls.core.basic.Result;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.mapper.TradeMapper;
import io.nuls.nulsswitch.service.TradeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.dto.order.QueryOrderResDto;
import io.nuls.nulsswitch.web.dto.order.QueryTradeReqDto;
import io.nuls.nulsswitch.web.dto.order.QueryTradeResDto;
import io.nuls.nulsswitch.web.exception.NulsRuntimeException;
import io.nuls.v2.util.NulsSDKTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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


    @Override
    public Page<Trade> queryTradeByOrderIdPage(QueryTradeReqDto reqDto) {
        Page<QueryTradeResDto> orderDtoPage = new Page<>();
        Page<Trade> tradePage = new Page<>();
        Trade example = new Trade();
        example.setOrderId(reqDto.getOrderId());
        tradePage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        tradePage.setSize(reqDto.getPageSize() == null ? 10 : reqDto.getPageSize());
        EntityWrapper<Trade> eWrapper = new EntityWrapper<>(example);
        eWrapper.orderBy("create_time", false);
        tradePage = this.selectPage(tradePage, eWrapper);
        return tradePage;
    }

    /**
     * 将数据广播到区块链
     * 更新交易状态
     */
    @Override
    public String broadcast(Trade trade, String txHex) {
        String hash;
        Result result = NulsSDKTool.broadcast(txHex);
        log.info("broadcast resp:{}", result);
        Map map = (Map) result.getData();
        if (map==null || map.get("hash") == null) {
            throw new NulsRuntimeException(CommonErrorCode.BROADCAST_ERROR);
        }
        hash = (String) map.get("hash");
        trade.setStatus(SwitchConstant.TX_TRADE_STATUS_CONFIRMING);
        trade.setTxHash(hash);
        updateById(trade);

        return hash;
    }
}
