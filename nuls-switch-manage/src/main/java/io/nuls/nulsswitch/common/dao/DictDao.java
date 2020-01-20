package io.nuls.nulsswitch.common.dao;

import java.util.List;

import io.nuls.nulsswitch.common.base.BaseDao;
import io.nuls.nulsswitch.common.domain.DictDO;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
public interface DictDao extends BaseDao<DictDO>{

    List<DictDO> listType();
    
}
