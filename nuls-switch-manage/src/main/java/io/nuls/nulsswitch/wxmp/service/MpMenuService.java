package io.nuls.nulsswitch.wxmp.service;

import io.nuls.nulsswitch.common.domain.Tree;
import io.nuls.nulsswitch.wxmp.domain.MpMenuDO;
import io.nuls.nulsswitch.common.base.CoreService;

/**
 * 
 * <pre>
 * 微信菜单表
 * </pre>
 * <small> 2018-10-19 15:47:16 | Aron</small>
 */
public interface MpMenuService extends CoreService<MpMenuDO> {

    Tree<MpMenuDO> getTree();

    void saveMenu(MpMenuDO mpMenu, String appId);
}
