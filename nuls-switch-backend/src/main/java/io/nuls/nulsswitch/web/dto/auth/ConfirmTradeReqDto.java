package io.nuls.nulsswitch.web.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类描述：交易发起方对交易进行确认请求，该请求会将交易发到区块链上
 *
 * @author Edward
 * @version v1.0
 * @date 2019/7/23 22:45
 */
@EqualsAndHashCode
@Data
@ApiModel
public class ConfirmTradeReqDto {

    private static final long serialVersionUID = 3967075132487249652L;

    /**
     * 签名原Hex
     */
    @ApiModelProperty(value = "交易16进制串")
    private String dataHex;
    /**
     * 公钥
     */
    @ApiModelProperty(value = "交易号")
    private String txId;

}
