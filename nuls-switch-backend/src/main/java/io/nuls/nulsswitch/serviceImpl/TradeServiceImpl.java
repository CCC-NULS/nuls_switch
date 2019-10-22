package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.core.basic.Result;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.mapper.TradeMapper;
import io.nuls.nulsswitch.service.TokenService;
import io.nuls.nulsswitch.service.TradeService;
import io.nuls.nulsswitch.util.NulsUtils;
import io.nuls.nulsswitch.util.StringUtils;
import io.nuls.nulsswitch.web.dto.order.QueryTradeReqDto;
import io.nuls.nulsswitch.web.exception.NulsRuntimeException;
import io.nuls.nulsswitch.web.vo.trade.TradeVO;
import io.nuls.v2.util.NulsSDKTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    TradeMapper tradeMapper;

    @Resource
    TokenService tokenService;

    @Override
    public Page<Trade> queryTradeByOrderIdPage(QueryTradeReqDto reqDto) {
        Page<Trade> tradePage = new Page<>();
        Trade trade = new Trade();
        trade.setOrderId(reqDto.getOrderId());
        tradePage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        tradePage.setSize(reqDto.getPageSize() == null ? 10 : reqDto.getPageSize());
        EntityWrapper<Trade> eWrapper = new EntityWrapper<>(trade);
        eWrapper.orderBy("create_time", false);
        tradePage = this.selectPage(tradePage, eWrapper);
        return tradePage;
    }

    @Override
    public Page<TradeVO> queryTradeByPage(QueryTradeReqDto reqDto) {
        Page<TradeVO> tradePage = new Page<>();
        Trade trade = new Trade();
        BeanUtils.copyProperties(reqDto, trade);
        tradePage.setCurrent(reqDto.getCurrent() == null ? 1 : reqDto.getCurrent());
        tradePage.setSize(reqDto.getPageSize() == null ? 10 : reqDto.getPageSize());
        tradePage.setRecords(tradeMapper.queryTradeByPage(tradePage, reqDto));
        Map<Integer, String> tokenMap = tokenService.getTokenMap();
        if (tradePage != null && tradePage.getRecords() != null) {
            tradePage.getRecords().forEach(obj -> {
                obj.setFromTokenName(tokenMap.get(obj.getFromTokenId()));
                obj.setToTokenName(tokenMap.get(obj.getToTokenId()));
                obj.setTokenPair(tokenMap.get(obj.getFromTokenId()) + "_" + tokenMap.get(obj.getToTokenId()));
            });
        }
        return tradePage;
    }

    @Override
    public String broadcast(Trade trade, String txHex) {
        String hash;
        Result result = NulsSDKTool.broadcast(txHex);
        log.info("broadcast resp:{}", result);
        Map map = (Map) result.getData();
        if (map == null || map.get("hash") == null) {
            throw new NulsRuntimeException(CommonErrorCode.BROADCAST_ERROR);
        }
        hash = (String) map.get("hash");
        trade.setStatus(SwitchConstant.TX_TRADE_STATUS_CONFIRMING);
        trade.setTxHash(hash);
        updateById(trade);

        return hash;
    }

    @Override
    public void cancelOrderTrade(String orderId, String tradeId) {
        if (StringUtils.isBlank(orderId) && StringUtils.isBlank(tradeId)) {
            throw new NulsRuntimeException(CommonErrorCode.PARAMETER_NULL);
        }
        tradeMapper.cancelOrderTrade(orderId, tradeId);
    }

    @Override
    public String queryLastTxHashByToken(String address, Integer tokenId) {
        List<TradeVO> tradeList = tradeMapper.queryTradeByToken(address, tokenId);
        if (tradeList != null && tradeList.size()>0) {
            return tradeList.get(0).getTxHash();
        }
        return null;
    }


    @Override
    public List<TradeVO> queryTradeByToken(String address, Integer tokenId) {
       return tradeMapper.queryTradeByToken(address, tokenId);
    }
}
