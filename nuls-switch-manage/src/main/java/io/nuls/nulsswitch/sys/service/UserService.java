package io.nuls.nulsswitch.sys.service;

import io.nuls.nulsswitch.common.base.CoreService;
import io.nuls.nulsswitch.common.domain.Tree;
import io.nuls.nulsswitch.sys.domain.DeptDO;
import io.nuls.nulsswitch.sys.domain.UserDO;
import io.nuls.nulsswitch.sys.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * 

 */
@Service
public interface UserService extends CoreService<UserDO> {

    boolean exist(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    int resetPwd(UserVO userVO, UserDO userDO);

    int adminResetPwd(UserVO userVO);

    Tree<DeptDO> getTree();

    /**
     * 更新个人信息
     * 
     * @param userDO
     * @return
     */
    int updatePersonal(UserDO userDO);

    /**
     * 更新个人图片
     * 
     * @param file
     *            图片
     * @param avatar_data
     *            裁剪信息
     * @param userId
     *            用户ID
     * @throws Exception
     */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
