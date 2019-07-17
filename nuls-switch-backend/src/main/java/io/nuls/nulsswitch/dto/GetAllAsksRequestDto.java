package io.nuls.nulsswitch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class GetAllAsksRequestDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;

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
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endQueryTime;
}
