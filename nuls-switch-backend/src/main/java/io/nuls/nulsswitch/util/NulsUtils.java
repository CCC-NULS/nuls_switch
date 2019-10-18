package io.nuls.nulsswitch.util;

/**
 * 类描述：NULS相关工具类
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/10/17 16:13
 */
public class NulsUtils {

    /**
     * 根据交易hash获取nonce值
     *
     * @param txHash
     * @return
     */
    public static String getNonceEncodeByTxHash(String txHash) {
        return txHash.substring(txHash.length() - 16);
    }

}
