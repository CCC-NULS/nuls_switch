package io.nuls.nulsswitch.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuls.nulsswitch.entity.UserAuth;
import io.nuls.nulsswitch.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 类描述：
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/19 17:33
 */
public class BaseController {

    @Autowired
    private UserAuthService userAuthService;

    protected boolean checkAuth(String token, String address) {
        EntityWrapper<UserAuth> wrapper = new EntityWrapper<>();
        wrapper.eq("token", token);
        wrapper.eq("address", address);
        // 根据地址查询token
        UserAuth userAuth = userAuthService.selectOne(wrapper);
        if (userAuth != null) {
            return true;
        } else {
            return false;
        }
    }
}
