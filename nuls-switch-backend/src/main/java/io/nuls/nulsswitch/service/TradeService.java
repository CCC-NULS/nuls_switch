package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.web.dto.order.QueryTradeReqDto;
import io.nuls.nulsswitch.web.vo.trade.TradeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录 服务类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface TradeService extends IService<Trade> {
    /**
     * 根据订单ID分页查询交易列表
     *
     * @param reqDto
     * @return
     */
    Page<Trade> queryTradeByOrderIdPage(QueryTradeReqDto reqDto);

    /**
     * 分页查询交易列表
     *
     * @param reqDto
     * @return
     */
    Page<TradeVO> queryTradeByPage(QueryTradeReqDto reqDto);

    /**
     * 将数据广播到区块链，并更新交易状态
     *
     * @param trade
     * @param txHex
     * @return
     */
    String broadcast(Trade trade, String txHex);

    /**
     * 取消订单交易，将用户未确认交易状态更新为取消
     *
     * @param orderId 订单ID
     * @param tradeId 交易ID
     */
    void cancelOrderTrade(String orderId, String tradeId);

    /**
     * 查询该地址+代币所有未确认交易
     *
     * @param address 地址
     * @param tokenId 代币ID
     * @return
     */
    List<TradeVO> queryTradeByToken(String address, Integer tokenId);

    /**
     * 查询该地址+代币当前最新的未确认交易hash
     *
     * @param address 地址
     * @param tokenId 代币ID
     * @return
     */
    String queryLastTxHashByToken(@Param("address") String address, @Param("tokenId") Integer tokenId);
}
