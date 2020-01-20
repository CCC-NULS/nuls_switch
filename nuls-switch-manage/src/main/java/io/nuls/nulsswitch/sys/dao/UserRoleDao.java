package io.nuls.nulsswitch.sys.dao;

import java.io.Serializable;
import java.util.List;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.sys.domain.RoleDO;
import io.nuls.nulsswitch.sys.domain.UserRoleDO;

/**
 * <pre>
 * 用户与角色对应关系
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface UserRoleDao extends BaseDao<UserRoleDO> {

	List<Long> listRoleId(Serializable userId);

	int removeByUserId(Serializable userId);

	int batchSave(List<UserRoleDO> list);

	int batchRemoveByUserId(Long[] ids);

    List<RoleDO> findListByUserId(Serializable id);
}
