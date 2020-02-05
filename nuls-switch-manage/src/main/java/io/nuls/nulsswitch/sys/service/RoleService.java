package io.nuls.nulsswitch.sys.service;

import io.nuls.nulsswitch.common.base.CoreService;
import io.nuls.nulsswitch.sys.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * </pre>

 */
@Service
public interface RoleService extends CoreService<RoleDO> {
    List<RoleDO> findAll();
    List<RoleDO> findListStatusByUserId(Serializable id);
    List<RoleDO> findListByUserId(Serializable id);
}
