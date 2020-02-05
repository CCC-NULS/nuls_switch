package io.nuls.nulsswitch.api.pojo.dto;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>

 */
@Data
public class UserLogoutDTO {
    private String token;
    private String refreshToken;
}
