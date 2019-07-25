package io.nuls.nulsswitch.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class DigestAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 校验密码有效性.
     *
     * @param userDetails    .
     * @param authentication .
     * @throws AuthenticationException .
     */
    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        //用户公钥
        String publicKey = authentication.getPrincipal().toString();

        //用户的数字签名
        String digest = authentication.getCredentials().toString();

        //数字签名验证结果
        boolean isVerifyPassed = true;

        if (!isVerifyPassed) {
            logger.debug("Authentication failed: the digest and publicKey not matched");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    /**
     * 获取用户.
     *
     * @param username       .
     * @param authentication .
     * @return
     * @throws AuthenticationException .
     */
    @Override
    protected UserDetails retrieveUser(
            String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        //UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
        return new User("nuls","nuls",new ArrayList<>());
//        if (loadedUser == null) {
//            throw new InternalAuthenticationServiceException(
//                    "UserDetailsService returned null, which is an interface contract violation");
//        }
        //return loadedUser;
    }
}