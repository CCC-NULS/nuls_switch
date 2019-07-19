
package io.nuls.nulsswitch.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 对于需要权限校验的请求需要继承自这个类，非查询类接口都应该校验用户token
 *
 * @author qinyifeng
 */
@Data
public class BaseReq<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限校验
     */
    private String token;

    /**
     * 参数
     */
    private T params;


}
