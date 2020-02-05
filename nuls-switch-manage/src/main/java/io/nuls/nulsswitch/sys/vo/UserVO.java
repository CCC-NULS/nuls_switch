package io.nuls.nulsswitch.sys.vo;

import io.nuls.nulsswitch.sys.domain.UserDO;
import lombok.Data;

/**
 * <pre>
 * </pre>

 */
@Data
public class UserVO {
    /**
     * 更新的用户对象
     */
    private UserDO userDO = new UserDO();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;
}
