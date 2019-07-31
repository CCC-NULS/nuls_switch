package io.nuls.nulsswitch.web.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.nuls.nulsswitch.web.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类描述：订单交易记录查询请求
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/31 01:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class QueryTradeReqDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;

	/**
	 * 交易流水号
	 */
	@ApiModelProperty(value = "交易流水号")
	private String txId;

	/**
	 * 委托挂单ID
	 */
	@ApiModelProperty(value = "委托挂单ID")
	private String orderId;

	/**
	 * 状态：0-未交易、1-部分交易、2-完成交易、9-撤销
	 */
	@ApiModelProperty(value = "交易状态")
	private Integer status;

}
