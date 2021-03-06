package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.entity.Order;
import io.nuls.nulsswitch.entity.Token;
import io.nuls.nulsswitch.entity.Trade;
import io.nuls.nulsswitch.entity.TxUnconfirmedNonce;
import io.nuls.nulsswitch.mapper.TokenMapper;
import io.nuls.nulsswitch.mapper.TxUnconfirmedNonceMapper;
import io.nuls.nulsswitch.service.TradeService;
import io.nuls.nulsswitch.service.TxUnconfirmedNonceService;
import io.nuls.nulsswitch.util.NulsUtils;
import io.nuls.nulsswitch.util.StringUtils;
import io.nuls.nulsswitch.web.vo.trade.TradeVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 支持交易的代币 服务实现类
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-16
 */
@Service
@Slf4j
public class TxUnconfirmedNonceServiceImpl extends ServiceImpl<TxUnconfirmedNonceMapper, TxUnconfirmedNonce> implements TxUnconfirmedNonceService {

    @Resource
    TxUnconfirmedNonceMapper txUnconfirmedNnoceMapper;
    @Resource
    TokenMapper tokenMapper;
    @Resource
    private TradeService tradeService;

    @Value("${nuls.token.chainId}")
    private Integer nulsChainId;
    @Value("${nuls.token.assetsId}")
    private Integer nulsAssetsId;

    @Override
    public void saveTxUnconfirmedNonce(String address, int assetsChainId, int assetsId, String txHash) {
        // 根据最新交易hash计算nonce
        String nonce = NulsUtils.getNonceEncodeByTxHash(txHash);
        // 新增交易nonce
        TxUnconfirmedNonce txUnconfirmedNonce = new TxUnconfirmedNonce();
        txUnconfirmedNonce.setAddress(address);
        txUnconfirmedNonce.setChainId(assetsChainId);
        txUnconfirmedNonce.setAssetId(assetsId);
        txUnconfirmedNonce.setTxHash(txHash);
        txUnconfirmedNonce.setNonce(nonce);
        txUnconfirmedNnoceMapper.insert(txUnconfirmedNonce);
    }

    @Override
    public void saveTxUnconfirmedNonce(TradeVO trade) {
        // 保存吃单用户转出资产nonce
        this.saveTxUnconfirmedNonce(trade.getAddress(), trade.getAssetsChainId(), trade.getAssetsId(), trade.getTxHash());

        // 保存挂单用户转出资产nonce
        this.saveTxUnconfirmedNonce(trade.getOrderAddress(), trade.getOrderAssetsChainId(), trade.getOrderAssetsId(), trade.getTxHash());
        // 如果挂单用户转出资产不等于手续费NULS资产，则保存NULS最新nonce
        if (!Objects.equals(trade.getOrderAssetsChainId(), trade.getNulsChainId()) || !Objects.equals(trade.getOrderAssetsId(), trade.getNulsAssetsId())) {
            this.saveTxUnconfirmedNonce(trade.getOrderAddress(), trade.getNulsChainId(), trade.getNulsAssetsId(), trade.getTxHash());
        }
    }

    @Override
    public void saveTxUnconfirmedNonceForTxFail(Trade trade, Order order, boolean isForTrade) {
        // 根据交易类型计算转出代币
        Integer tradeTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getFromTokenId() : order.getToTokenId();
        Integer orderTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getToTokenId() : order.getFromTokenId();
        Token tradeToken = tokenMapper.selectById(tradeTokenId);
        Token orderToken = tokenMapper.selectById(orderTokenId);

        Date tradeDate = trade.getCreateTime();
        //if (isForTrade) {
        String address = trade.getAddress();
        // 查询该地址+代币所有未确认交易，作为本地最新未确认nonce，便于该token再进行交易时，能接上之前未确认的交易
        String lastTxHash = tradeService.queryLastTxHashByToken(address, null, tradeTokenId, tradeDate);

        if (StringUtils.isNotBlank(lastTxHash)) {
            // 保存吃单用户转出资产nonce
            this.saveTxUnconfirmedNonce(address, tradeToken.getChainId(), tradeToken.getAssetId(), lastTxHash);
            log.info("saveTxUnconfirmedNonce txHash:{},tradeAddress:{},chainId:{},assetId:{}", lastTxHash, address, tradeToken.getChainId(), tradeToken.getAssetId());
        }
        //}

        String orderLastTxHash = tradeService.queryLastTxHashByTokenAndOrder(order.getOrderId(), orderTokenId, tradeDate);
        if (StringUtils.isNotBlank(orderLastTxHash)) {
            // 保存挂单用户转出资产nonce
            this.saveTxUnconfirmedNonce(order.getAddress(), orderToken.getChainId(), orderToken.getAssetId(), orderLastTxHash);
            log.info("saveTxUnconfirmedNonce txHash:{},orderAddress:{},chainId:{},assetId:{}", orderLastTxHash, order.getAddress(), orderToken.getChainId(), orderToken.getAssetId());

            if (!Objects.equals(orderToken.getChainId(), nulsChainId) || !Objects.equals(orderToken.getAssetId(), nulsAssetsId)) {
                this.saveTxUnconfirmedNonce(order.getAddress(), nulsChainId, nulsAssetsId, orderLastTxHash);
                log.info("saveTxUnconfirmedNonce txHash:{},orderAddress:{},nulsChainId:{},nulsAssetsId:{}", orderLastTxHash, order.getAddress(), nulsChainId, nulsAssetsId);
            }
        }
    }

    @Override
    public String getTxNonce(String address, int assetsChainId, int assetsId) {
        TxUnconfirmedNonce unconfirmedNonce = this.getTxUnconfirmedNonce(address, assetsChainId, assetsId);
        if (unconfirmedNonce != null) {
            return unconfirmedNonce.getNonce();
        }
        return "";
    }

    public TxUnconfirmedNonce getTxUnconfirmedNonce(String address, int assetsChainId, int assetsId) {
        TxUnconfirmedNonce txUnconfirmedNonce = new TxUnconfirmedNonce();
        txUnconfirmedNonce.setAddress(address);
        txUnconfirmedNonce.setChainId(assetsChainId);
        txUnconfirmedNonce.setAssetId(assetsId);
        EntityWrapper<TxUnconfirmedNonce> eWrapper = new EntityWrapper<>(txUnconfirmedNonce);
        eWrapper.orderBy("create_time", false);
        List<TxUnconfirmedNonce> nonceList = Optional.ofNullable(txUnconfirmedNnoceMapper.selectList(eWrapper)).orElse(Collections.emptyList());
        if (nonceList != null && nonceList.size() > 0) {
            return nonceList.get(0);
        }
        return null;
    }

    @Override
    public void deleteNonceByTrade(Trade trade, Order order) {
        // 需要重新计算nonce的代币
        Integer tradeTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getFromTokenId() : order.getToTokenId();
        Integer orderTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getToTokenId() : order.getFromTokenId();
        Token tradeToken = tokenMapper.selectById(tradeTokenId);
        Token orderToken = tokenMapper.selectById(orderTokenId);

        this.deleteNonceByAssets(trade.getAddress(), trade.getTxHash(), tradeToken.getChainId(), tradeToken.getAssetId(), trade.getCreateTime());
        log.info("deleteNonceByAddress tradeAddress:{},txHash:{},chainId:{},assetId:{}", trade.getAddress(), trade.getTxHash(), tradeToken.getChainId(), tradeToken.getAssetId());

        this.deleteNonceByAssets(order.getAddress(), trade.getTxHash(), orderToken.getChainId(), orderToken.getAssetId(), trade.getCreateTime());
        log.info("deleteNonceByAddress orderAddress:{},txHash:{},chainId:{},assetId:{}", order.getAddress(), trade.getTxHash(), orderToken.getChainId(), orderToken.getAssetId());

        // 挂单地址一定会转出NULS
        this.deleteNonceByAssets(order.getAddress(), trade.getTxHash(), nulsChainId, nulsAssetsId, trade.getCreateTime());
        log.info("deleteNonceByAddress orderAddress:{},txHash:{},chainId:{},assetId:{}", order.getAddress(), trade.getTxHash(), nulsChainId, nulsAssetsId);
    }

    @Override
    public void deleteAndSaveNonceByTradeOrder2(Trade trade, Order order) {
        // 需要重新计算nonce的代币
        Integer tradeTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getFromTokenId() : order.getToTokenId();
        Integer orderTokenId = order.getTxType() == SwitchConstant.TX_TYPE_BUY ? order.getToTokenId() : order.getFromTokenId();
        String txHash = tradeService.queryLastTxHashByToken(trade.getAddress(), null, tradeTokenId, null);

        // 如果该地址没有未确认交易，则删除本地未确认交易nonce
        if (StringUtils.isBlank(txHash)) {
            Token tradeToken = tokenMapper.selectById(tradeTokenId);
            Token orderToken = tokenMapper.selectById(orderTokenId);

            this.deleteNonceByAssets(trade.getAddress(), trade.getTxHash(), tradeToken.getChainId(), tradeToken.getAssetId(), trade.getCreateTime());
            log.info("deleteNonceByAddress tradeAddress:{},chainId:{},assetId:{}", trade.getAddress(), tradeToken.getChainId(), tradeToken.getAssetId());

            String orderTxHash = tradeService.queryLastTxHashByToken(null, order.getOrderId(), orderTokenId, null);
            // 如果该订单相关地址没有未确认交易，则删除本地未确认交易nonce
            if (StringUtils.isBlank(orderTxHash)) {
                this.deleteNonceByAssets(order.getAddress(), trade.getTxHash(), orderToken.getChainId(), orderToken.getAssetId(), trade.getCreateTime());
                log.info("deleteNonceByAddress orderAddress:{},chainId:{},assetId:{}", order.getAddress(), orderToken.getChainId(), orderToken.getAssetId());

                // 挂单地址一定会转出NULS
                this.deleteNonceByAssets(order.getAddress(), trade.getTxHash(), nulsChainId, nulsAssetsId, trade.getCreateTime());
                log.info("deleteNonceByAddress orderAddress:{},chainId:{},assetId:{}", order.getAddress(), nulsChainId, nulsAssetsId);
            }
        }
    }

    @Override
    public void deleteNonceByAddressAndHash(String tradeAddress, String orderAddress, String txHash, Date createTime) {
        this.deleteNonceByAssets(tradeAddress, txHash, null, null, createTime);
        log.info("deleteNonceByAddress address:{},txHash:{}", tradeAddress, txHash);

        this.deleteNonceByAssets(orderAddress, txHash, null, null, createTime);
        log.info("deleteNonceByAddress address:{},txHash:{}", orderAddress, txHash);

    }

    @Override
    public void deleteNonceByAddress(String tradeAddress, String orderAddress, String txHash, Integer assetsChainId, Integer assetsId, Date createTime) {
        this.deleteNonceByAssets(tradeAddress, txHash, assetsChainId, assetsId, createTime);
        log.info("deleteNonceByAddress address:{},txHash:{},chainId:{},assetId:{}", tradeAddress, txHash, assetsChainId, assetsId);

        this.deleteNonceByAssets(orderAddress, txHash, assetsChainId, assetsId, createTime);
        log.info("deleteNonceByAddress address:{},txHash:{},chainId:{},assetId:{}", orderAddress, txHash, assetsChainId, assetsId);

        this.deleteNonceByAssets(orderAddress, txHash, nulsChainId, nulsAssetsId, createTime);
        log.info("deleteNonceByAddress address:{},txHash:{},chainId:{},assetId:{}", orderAddress, txHash, nulsChainId, nulsAssetsId);
    }

    @Override
    public void deleteNonceByAssets(String address, String txHash, Integer assetsChainId, Integer assetsId, Date createTime) {
        // 删除当前地址+交易hash+资产的nonce
        TxUnconfirmedNonce txUnconfirmedNonce = new TxUnconfirmedNonce();
        txUnconfirmedNonce.setAddress(address);
        txUnconfirmedNonce.setTxHash(txHash);
        txUnconfirmedNonce.setChainId(assetsChainId);
        txUnconfirmedNonce.setAssetId(assetsId);
        EntityWrapper<TxUnconfirmedNonce> eWrapper = new EntityWrapper<>(txUnconfirmedNonce);
        txUnconfirmedNnoceMapper.delete(eWrapper);

        // 删除当前地址+资产，在当前交易之后交易的nonce
        txUnconfirmedNnoceMapper.delete(eWrapper);
        if (createTime != null) {
            txUnconfirmedNonce.setTxHash(null);
            eWrapper = new EntityWrapper<>(txUnconfirmedNonce);
            eWrapper.ge("create_time", createTime);
        }
        txUnconfirmedNnoceMapper.delete(eWrapper);
    }

    @Override
    public void deleteNonceByTxhash(String address, String txHash) {
        TxUnconfirmedNonce txUnconfirmedNonce = new TxUnconfirmedNonce();
        txUnconfirmedNonce.setAddress(address);
        txUnconfirmedNonce.setTxHash(txHash);
        EntityWrapper<TxUnconfirmedNonce> eWrapper = new EntityWrapper<>(txUnconfirmedNonce);
        txUnconfirmedNnoceMapper.delete(eWrapper);
    }

    @Override
    public void deleteNonceByOrderId(String orderId) {
        txUnconfirmedNnoceMapper.deleteNonceByOrderId(orderId);
    }
}
