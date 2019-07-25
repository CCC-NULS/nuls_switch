package io.nuls.nulsswitch.serviceImpl;

import io.nuls.base.basic.AddressTool;
import io.nuls.core.crypto.ECKey;
import io.nuls.core.crypto.HexUtil;
import io.nuls.nulsswitch.constant.ResultCode;
import io.nuls.nulsswitch.entity.UserAuth;
import io.nuls.nulsswitch.mapper.UserAuthMapper;
import io.nuls.nulsswitch.security.JwtUtils;
import io.nuls.nulsswitch.service.AuthService;
import io.nuls.nulsswitch.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author: EdwardChan
 * createAt: Jul. 20th 2019
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    JwtUtils jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String getToken(String dataHex, String publicKeyHex, String signatureHex) {
        String address;
        boolean verifyResult = false;
        byte[] dataBytes, pubBytes = null;
        try {
            dataBytes = HexUtil.decode(dataHex);
            byte[] signatureBytes = HexUtil.decode(signatureHex);
            pubBytes = HexUtil.decode(publicKeyHex);
            verifyResult = ECKey.verify(dataBytes, signatureBytes, pubBytes);
        } catch (Exception e) {
            log.error("", e);
        }
        Assert.isTrue(verifyResult,"数字签名验证失败");
        address = AddressTool.getStringAddressByBytes(AddressTool.getAddress(pubBytes,2));
        String token = jwtTokenUtil.generateAccessToken(address);
        UserAuth userAuth = new UserAuth();
        userAuth.setAddress(address);
        userAuth.setToken(token);
        userAuthMapper.insert(userAuth);
        return token;
    }

    @Override
    public void logout(String token) {
        token = token.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
    }

    private Authentication authenticate(String publicKey, String digest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(publicKey, digest));
        } catch (DisabledException | BadCredentialsException e) {
            throw new BusinessException(ResultCode.LOGIN_ERROR.getCode(), e.getMessage());
        }
    }
}
