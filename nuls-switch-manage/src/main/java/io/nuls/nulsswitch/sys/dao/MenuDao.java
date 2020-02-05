package io.nuls.nulsswitch.sys.dao;

import java.util.List;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.sys.domain.MenuDO;

/**
 * <pre>
 * 菜单管理
 * </pre>

 */
public interface MenuDao extends BaseDao<MenuDO> {
	
	List<MenuDO> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);
}
