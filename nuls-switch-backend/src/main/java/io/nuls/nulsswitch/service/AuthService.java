package io.nuls.nulsswitch.service;

import io.nuls.nulsswitch.security.ResponseUserToken;

/**
 * @author: EdwardChan
 * createAt: Jul. 20th 2019
 */
public interface AuthService {

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    String getToken(String dataHex, String signatureHex, String publicKeyHex);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

}
