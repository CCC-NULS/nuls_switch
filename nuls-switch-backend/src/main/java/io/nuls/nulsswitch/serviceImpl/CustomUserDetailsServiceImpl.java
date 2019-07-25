package io.nuls.nulsswitch.serviceImpl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登陆身份认证
 *
 * @author: EdwardChan
 * createAt: Jul. 20th 2019
 */
@Component(value = "CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public User loadUserByUsername(String name) throws UsernameNotFoundException {

        return null;
        //User userDetail = new User(null, null, null);

        //return userDetail;

//        UserDetail userDetail = authMapper.findByUsername(name);
//        if (userDetail == null) {
//            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
//        }
//        Role role = authMapper.findRoleByUserId(userDetail.getId());
//        userDetail.setRole(role);
//        return userDetail;
    }
}
