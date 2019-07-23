package io.nuls.nulsswitch.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuls.nulsswitch.constant.CommonErrorCode;
import io.nuls.nulsswitch.entity.UserAuth;
import io.nuls.nulsswitch.service.UserAuthService;
import io.nuls.nulsswitch.util.StringUtils;
import io.nuls.nulsswitch.web.exception.NulsRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 类描述：
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/19 17:33
 */
@CrossOrigin(origins = "*", maxAge = 3600)
public class BaseController {

    @Autowired
    private UserAuthService userAuthService;

    protected boolean checkAuth(String token, String address) {
        EntityWrapper<UserAuth> wrapper = new EntityWrapper<>();
        wrapper.eq("token", token);
        if (StringUtils.isNotBlank(address)) {
            wrapper.eq("address", address);
        }
        // 根据地址查询token
        UserAuth userAuth = userAuthService.selectOne(wrapper);
        if (userAuth != null) {
            return true;
        } else {
            throw new NulsRuntimeException(CommonErrorCode.NO_AUTH);
        }
    }
}
