
package io.nuls.nulsswitch.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 对于分页的请求需要继承自这个类
 */
@Data
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 3319698607712846427L;

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页条数
     */
    private Integer size = 10;

    /**
     * 排序
     */
    private String orderBy;
}
