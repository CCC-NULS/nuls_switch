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
	 * 原token
	 */
	@ApiModelProperty(value = "原token")
	private Integer fromTokenId;
	/**
	 * 目标Token
	 */
	@ApiModelProperty(value = "目标Token")
	private Integer toTokenId;

	/**
	 * 用户地址
	 */
	@ApiModelProperty(value = "用户地址")
	private String address;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endQueryTime;

	/**
	 * 订单类型：1-买入、2-卖出
	 */
	@ApiModelProperty(value = "订单类型")
	private Integer txType;

	/**
	 * 状态：0-交易未确认、1-交易确认中、2-交易完成确认、3-交易失败、9-撤销
	 */
	@ApiModelProperty(value = "交易状态")
	private Integer status;

}
