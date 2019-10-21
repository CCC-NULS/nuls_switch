package io.nuls.nulsswitch.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
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
     * @param nonce         本地最新未确认交易nonce
     */
    void saveTxUnconfirmedNonce(String address, int assetsChainId, int assetsId, String nonce);

    /**
     * 查询该地址某资产本地未确认交易nonce
     *
     * @param address       交易地址
     * @param assetsChainId 资产链ID
     * @param assetsId      资产ID
     * @return
     */
    String getTxNonce(String address, int assetsChainId, int assetsId);
}
