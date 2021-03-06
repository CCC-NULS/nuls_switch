package io.nuls.nulsswitch.sys.dao;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>

 */
public interface DeptDao extends BaseDao<DeptDO> {
	
	Long[] listParentDept();
	
	int getDeptUserNumber(Long deptId);
}
