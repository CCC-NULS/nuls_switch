package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuls.nulsswitch.entity.Order;
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
     * @param txHash  本地最新未确认交易hash
     */
    void saveTxUnconfirmedNonce(String address, Order order, String txHash);

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
     * 删除该地址指定交易本地未确认交易nonce
     *
     * @param address 交易地址
     * @param txHash  交易hash
     */
    void deleteNonceByTxhash(String address, String txHash);
}
