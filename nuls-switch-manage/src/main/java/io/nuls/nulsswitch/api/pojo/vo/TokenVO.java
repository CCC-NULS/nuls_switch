package io.nuls.nulsswitch.api.pojo.vo;

import lombok.Data;

/**
 * <pre>
 * </pre>
 * 
 *
 */
@Data
public class TokenVO {
    private String token;
    private Long tokenExpire;
    private String refleshToken;
    private Long refreshTokenExpire;

}
