package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.entity.TxUnconfirmedNonce;
import io.nuls.nulsswitch.web.dto.order.QueryOrderReqDto;
import io.nuls.nulsswitch.web.dto.order.QueryOrderResDto;

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
     * 根据保存该地址某资产本地未确认交易nonce
     *
     * @param address 交易地址
     * @param order   订单
     */
    void saveTxUnconfirmedNonce(String address, Order order);

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
     *
     * @param trade
     * @param order
     */
    void deleteNonceByTradeOrder(Trade trade, Order order);

    /**
     * 删除该地址某资产本地未确认交易nonce，包括吃单、挂单地址
     *
     * @param tradeAddress  交易地址
     * @param orderAddress  挂单地址
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     */
    void deleteNonceByAddress(String tradeAddress, String orderAddress, int assetsChainId, int assetsId);

    /**
     * 删除该地址某资产本地未确认交易nonce
     *
     * @param address       交易地址
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     */
    void deleteNonceByAssets(String address, int assetsChainId, int assetsId);

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
