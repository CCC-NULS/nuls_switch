package io.nuls.nulsswitch.sys.dao;

import java.io.Serializable;
import java.util.List;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.sys.domain.RoleMenuDO;

/**
 * <pre>
 * 角色与菜单对应关系
 * </pre>

 */
public interface RoleMenuDao extends BaseDao<RoleMenuDO> {
	
	List<Long> listMenuIdByRoleId(Serializable roleId);
	
	int removeByRoleId(Serializable roleId);
	
	int batchSave(List<RoleMenuDO> list);
}
