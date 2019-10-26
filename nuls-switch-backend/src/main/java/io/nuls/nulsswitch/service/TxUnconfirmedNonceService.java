package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.entity.TxUnconfirmedNonce;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.dto.order.QueryOrderResDto;
import io.nuls.nulsswitch.web.vo.trade.TradeVO;

import java.util.Date;

/**
 * <p>
 * 本地未确认交易nonce，服务类
 * </p>
 *
 * @author qinyifeng
 * @since 2019-10-21
 */
public interface TxUnconfirmedNonceService extends IService<TxUnconfirmedNonce> {

    /**
     * 保存该地址某资产本地未确认交易nonce
     *
     * @param address       交易地址
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     * @param txHash        本地最新未确认交易hash
     */
    void saveTxUnconfirmedNonce(String address, int assetsChainId, int assetsId, String txHash);

    /**
     * 保存该吃单地址（转出资产）、挂单地址（转出资产、手续费NULS）本地未确认交易nonce
     *
     * @param trade 交易
     */
    void saveTxUnconfirmedNonce(TradeVO trade);

    /**
     * 针对取消委托、交易失败等异常情况，根据保存该地址某资产本地未确认交易nonce
     *
     * @param trade      交易
     * @param order      订单
     * @param isForTrade 订单是否重新计算nonce
     */
    void saveTxUnconfirmedNonceForTxFail(Trade trade, Order order, boolean isForTrade);

    /**
     * 查询该地址某资产本地未确认交易nonce
     *
     * @param address       交易地址
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     * @return
     */
    String getTxNonce(String address, int assetsChainId, int assetsId);

    /**
     * 删除该交易相关地址某资产本地未确认交易nonce，包括吃单地址、挂单地址
     *
     * @param trade
     * @param order
     */
    void deleteNonceByTrade(Trade trade, Order order);

    /**
     * 删除该交易相关地址某资产本地未确认交易nonce，包括吃单地址、挂单地址
     * 并计算保存挂单地址最新nonce
     *
     * @param trade
     * @param order
     */
    void deleteAndSaveNonceByTradeOrder2(Trade trade, Order order);

    /**
     * 删除该地址某资产本地未确认交易nonce，包括吃单、挂单地址
     *
     * @param tradeAddress  交易地址
     * @param orderAddress  挂单地址
     * @param txHash        交易hash
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     * @param createTime    交易时间
     */
    void deleteNonceByAddress(String tradeAddress, String orderAddress, String txHash, Integer assetsChainId, Integer assetsId, Date createTime);

    /**
     * 删除该地址某资产本地未确认交易nonce，包括吃单、挂单地址
     * 如果取消交易或者交易失败等异常情况，必须传入交易时间用于删除该失败交易之后的所有交易nonce
     *
     * @param tradeAddress 交易地址
     * @param orderAddress 挂单地址
     * @param txHash       交易hash
     * @param createTime   交易时间
     */
    void deleteNonceByAddressAndHash(String tradeAddress, String orderAddress, String txHash, Date createTime);

    /**
     * 删除该地址某资产本地未确认交易nonce
     *
     * @param address       交易地址
     * @param txHash        交易hash
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     * @param createTime    交易时间
     */
    void deleteNonceByAssets(String address, String txHash, Integer assetsChainId, Integer assetsId, Date createTime);

    /**
     * 删除该地址指定交易本地未确认交易nonce
     *
     * @param address 交易地址
     * @param txHash  交易hash
     */
    void deleteNonceByTxhash(String address, String txHash);

    /**
     * 删除该订单所有未确认交易nonce
     *
     * @param orderId 订单号
     */
    void deleteNonceByOrderId(String orderId);
}
