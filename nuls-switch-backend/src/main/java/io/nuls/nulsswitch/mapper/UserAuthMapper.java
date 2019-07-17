package io.nuls.nulsswitch.mapper;

import io.nuls.nulsswitch.entity.UserAuth;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户认证表：存储用户登录token与钱包地址关系 Mapper 接口
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
public interface UserAuthMapper extends BaseMapper<UserAuth> {

}
