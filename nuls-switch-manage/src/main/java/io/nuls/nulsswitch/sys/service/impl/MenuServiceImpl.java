package io.nuls.nulsswitch.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.nuls.nulsswitch.common.domain.Tree;
import io.nuls.nulsswitch.common.utils.BuildTree;
import io.nuls.nulsswitch.sys.dao.MenuDao;
import io.nuls.nulsswitch.sys.dao.RoleMenuDao;
import io.nuls.nulsswitch.sys.domain.MenuDO;
import io.nuls.nulsswitch.sys.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.nuls.nulsswitch.common.base.CoreServiceImpl;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends CoreServiceImpl<MenuDao, MenuDO> implements MenuService {

    @Autowired
    RoleMenuDao roleMenuMapper;

    /**
     * @param
     * @return 树形菜单
     */
    @Cacheable
    @Override
    public Tree<MenuDO> getSysMenuTree(Long id) {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<MenuDO> getTree() {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.selectList(null);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<MenuDO> getTree(Long id) {
        // 根据roleId查询权限
        List<MenuDO> menus = baseMapper.selectList(null);
        List<Long> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
        List<Long> temp = menuIds;
        for (MenuDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.selectList(null);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = sysMenuDO.getId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = baseMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Tree<MenuDO>> listMenuTree(Long id) {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<MenuDO>> list = BuildTree.buildList(trees, "0");
        return list;
    }

}
