package io.nuls.nulsswitch.sys.service;

import java.util.List;
import java.util.Set;

import io.nuls.nulsswitch.common.domain.Tree;
import io.nuls.nulsswitch.sys.domain.MenuDO;
import org.springframework.stereotype.Service;

import io.nuls.nulsswitch.common.base.CoreService;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface MenuService extends CoreService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);
}
