package io.nuls.nulsswitch.sys.service;

import io.nuls.nulsswitch.common.base.CoreService;
import io.nuls.nulsswitch.common.domain.Tree;
import io.nuls.nulsswitch.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptService extends CoreService<DeptDO> {
    
	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
