package io.nuls.nulsswitch.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.nuls.nulsswitch.api.config.JWTConfigProperties;
import io.nuls.nulsswitch.api.exception.IFastApiException;
import io.nuls.nulsswitch.api.pojo.vo.TokenVO;
import io.nuls.nulsswitch.api.service.impl.AppUserServiceImpl;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import io.nuls.nulsswitch.common.utils.SpringContextHolder;
import io.nuls.nulsswitch.sys.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * jwt工具类
 * </pre>
 *
 * <small> 2018年4月28日 | Aron</small>
 */
@Slf4j
@Component
public class JWTUtil {

    private static JWTConfigProperties jwtConfigProperties = null;

    @PostConstruct
    public void init() {
        jwtConfigProperties = SpringContextHolder.getBean(JWTConfigProperties.class);
    }

    public static String STR_DELIMITER = "\\.";
    public static int LEVEL = 3;


    public static TokenVO createToken(UserDO user) {
        TokenVO vo = new TokenVO();
        String token = JWTUtil.sign(user.getId() + "", user.getUsername() + user.getPassword(), AppUserServiceImpl.Holder.jwtConfig.getExpireTime());
        String refreshToken = JWTUtil.sign(user.getId() + "", user.getUsername() + user.getPassword(), AppUserServiceImpl.Holder.jwtConfig.getRefreshTokenExpire(), true);
        vo.setToken(token);
        vo.setRefleshToken(refreshToken);
        vo.setTokenExpire(AppUserServiceImpl.Holder.jwtConfig.getExpireTime());
        vo.setRefreshTokenExpire(AppUserServiceImpl.Holder.jwtConfig.getRefreshTokenExpire());
        return vo;
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * <pre>
     * </pre>
     *
     * <small> 2018年4月28日 | Aron</small>
     *
     * @param token  即jwt
     * @param userId 用户id
     * @param secret 用户的secret
     */
    public static void verify(String token, String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(jwtConfigProperties.getUserPrimaryKey(), userId).build();
            verifier.verify(token);
        } catch (TokenExpiredException exception) {
            log.info("token 签名校验失败,过期：{}", token);
            throw new ExpiredCredentialsException(EnumErrorCode.apiAuthorizationExpired.getMsg());
        } catch (InvalidClaimException exception2) {
            log.info("token 签名校验失败,数据异常：{}", token);
            throw new AuthenticationException(EnumErrorCode.apiAuthorizationInvalid.getMsg());
        } catch (Exception exception3) {
            log.info("token 签名校验失败：{}", token);
            throw new IFastApiException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }
    }

    public static void verify(String token, String userId, String secret, boolean isRefreshToken) {
        if (isRefreshToken) {
            secret += "_REFRESH_TOKEN";
        }
        verify(token, userId, secret);
    }

    /**
     * <pre>
     * 获得token中的信息无需secret解密也能获得
     * </pre>
     *
     * <small> 2018年4月28日 | Aron</small>
     *
     * @param token token
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim(jwtConfigProperties.getUserPrimaryKey());
            return claim.asString();
        } catch (JWTDecodeException e) {
            log.warn("token解码获取{}失败：{}", jwtConfigProperties.getUserPrimaryKey(), token);
            return null;
        }
    }

    /**
     * <pre>
     * </pre>
     *
     * <small> 2018年4月28日 | Aron</small>
     *
     * @param userId 用户标识
     * @param secret 加密密钥
     * @param expire 有效期，毫秒值
     */
    public static String sign(String userId, String secret, long expire) {
        try {
            Date date = new Date(System.currentTimeMillis() + expire);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withClaim(jwtConfigProperties.getUserPrimaryKey(), userId).withExpiresAt(date).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IFastApiException(EnumErrorCode.apiAuthorizationSignFailed.getCodeStr());
        }
    }

    /**
     * <pre>
     * </pre>
     *
     * <small> 2018年4月28日 | Aron</small>
     *
     * @param userId 用户标识
     * @param secret 加密密钥
     * @param expire 有效期，毫秒值
     */
    public static String sign(String userId, String secret, long expire, boolean isRefreshToken) {
        if (isRefreshToken) {
            secret += "_REFRESH_TOKEN";
        }
        return sign(userId, secret, expire);
    }
}
