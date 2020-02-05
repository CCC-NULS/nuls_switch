package io.nuls.nulsswitch.sys.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.nuls.nulsswitch.sys.dao.RoleDao;
import io.nuls.nulsswitch.sys.dao.RoleMenuDao;
import io.nuls.nulsswitch.sys.dao.UserDao;
import io.nuls.nulsswitch.sys.dao.UserRoleDao;
import io.nuls.nulsswitch.sys.domain.RoleDO;
import io.nuls.nulsswitch.sys.domain.RoleMenuDO;
import io.nuls.nulsswitch.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.nuls.nulsswitch.common.base.CoreServiceImpl;

/**
 * <pre>
 * </pre>

 */
@Service
public class RoleServiceImpl extends CoreServiceImpl<RoleDao, RoleDO> implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleMenuDao roleMenuMapper;
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;

    @Cacheable(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public List<RoleDO> findAll() {
        List<RoleDO> roles = selectList(null);
        return roles;
    }

    @Override
    public List<RoleDO> findListStatusByUserId(Serializable userId) {
        List<Long> rolesIds = userRoleMapper.listRoleId(userId);
        List<RoleDO> roles = selectList(null);
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<RoleDO> findListByUserId(Serializable id) {
        return userRoleMapper.findListByUserId(id);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean insert(RoleDO role) {
        int count = baseMapper.insert(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean deleteById(Serializable id) {
        int count = baseMapper.deleteById(id);
        roleMenuMapper.removeByRoleId(id);
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public boolean updateById(RoleDO role) {
        int r = baseMapper.updateById(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(r);
    }

}
