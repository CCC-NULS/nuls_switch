package io.nuls.nulsswitch.wxmp.service;

import io.nuls.nulsswitch.wxmp.domain.MpFansDO;
import io.nuls.nulsswitch.common.base.CoreService;

import java.util.List;

/**
 * 
 * <pre>
 * 微信粉丝表
 * </pre>
 */
public interface MpFansService extends CoreService<MpFansDO> {

    /**
     * <pre>
     * 根据openid查表是否存在，如果存在则更新fans数据
     * </pre>
     *
     * 
     * @param fans
     */
    void sync(MpFansDO fans);
    void sync(List<Long> ids);

    void syncWxMp(String appId);
}
