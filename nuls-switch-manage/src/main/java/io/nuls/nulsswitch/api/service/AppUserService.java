package io.nuls.nulsswitch.api.service;

import io.nuls.nulsswitch.api.pojo.vo.TokenVO;
import io.nuls.nulsswitch.common.base.CoreService;
import io.nuls.nulsswitch.sys.domain.UserDO;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <pre>
 * </pre>
 *
 */
public interface AppUserService extends CoreService<UserDO> {
    /** 申请token */
    TokenVO getToken(UsernamePasswordToken token) ;
    /** 刷新token */
    TokenVO refreshToken(String uname, String refreshToken);
    /** 检查token是否有效：未超时、未注销*/
    void verifyToken(String token, boolean isRefresh);
    /** 注销token */
    void logoutToken(String token, String refreshToken);
}
