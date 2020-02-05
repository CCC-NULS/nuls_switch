package io.nuls.nulsswitch.sys.dao;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.sys.domain.UserDO;

/**
 * <pre>
 * </pre>

 */
public interface UserDao extends BaseDao<UserDO> {
	
	Long[] listAllDept();

}
