package io.nuls.nulsswitch.serviceImpl;

import io.nuls.nulsswitch.entity.UserAuth;
import io.nuls.nulsswitch.mapper.UserAuthMapper;
import io.nuls.nulsswitch.service.UserAuthService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户认证表：存储用户登录token与钱包地址关系 服务实现类
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
