package io.nuls.nulsswitch.web.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.nuls.nulsswitch.entity.Order;
import lombok.Data;

/**
 * <p>
 * 查询订单响应数据
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryTradeResDto extends Order {

    private static final long serialVersionUID = 1L;
    

}
