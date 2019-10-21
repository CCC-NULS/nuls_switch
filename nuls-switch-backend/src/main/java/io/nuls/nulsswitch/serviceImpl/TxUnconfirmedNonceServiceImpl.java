package io.nuls.nulsswitch.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuls.nulsswitch.entity.TxUnconfirmedNonce;
import io.nuls.nulsswitch.mapper.TxUnconfirmedNnoceMapper;
import io.nuls.nulsswitch.service.TxUnconfirmedNonceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 支持交易的代币 服务实现类
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-16
 */
@Service
public class TxUnconfirmedNonceServiceImpl extends ServiceImpl<TxUnconfirmedNnoceMapper, TxUnconfirmedNonce> implements TxUnconfirmedNonceService {

    @Resource
    TxUnconfirmedNnoceMapper txUnconfirmedNnoceMapper;

    @Override
    public void saveTxUnconfirmedNonce(String address, int assetsChainId, int assetsId, String nonce) {
        // 如果已存在nonce记录，则更新
        TxUnconfirmedNonce unconfirmedNonce = this.getTxUnconfirmedNonce(address, assetsChainId, assetsId);
        if (unconfirmedNonce != null) {
            unconfirmedNonce.setNonce(nonce);
            txUnconfirmedNnoceMapper.updateById(unconfirmedNonce);
        } else {
            // 不存在nonce记录，则新增
            TxUnconfirmedNonce txUnconfirmedNonce = new TxUnconfirmedNonce();
            txUnconfirmedNonce.setAddress(address);
            txUnconfirmedNonce.setChainId(assetsChainId);
            txUnconfirmedNonce.setAssetId(assetsId);
            txUnconfirmedNonce.setNonce(nonce);
            txUnconfirmedNnoceMapper.insert(txUnconfirmedNonce);
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
}
