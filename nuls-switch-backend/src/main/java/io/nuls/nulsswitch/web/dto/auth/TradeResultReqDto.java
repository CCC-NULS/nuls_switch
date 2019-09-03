package io.nuls.nulsswitch.web.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类描述：交易验证、广播失败时，修改交易状态，错误原因
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/9/3 16:45
 */
@EqualsAndHashCode
@Data
@ApiModel
public class TradeResultReqDto {

    /**
     * 公钥
     */
    @ApiModelProperty(value = "交易号")
    private String txId;

    /**
     * 签名原Hex
     */
    @ApiModelProperty(value = "交易16进制串")
    private String dataHex;

    /**
     * 交易验证、广播返回结果
     */
    @ApiModelProperty(value = "交易验证、广播返回结果")
    private String msg;
}
