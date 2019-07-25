package io.nuls.nulsswitch.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.nuls.nulsswitch.web.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类描述：订单查询请求
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/18 21:40
 */
@EqualsAndHashCode
@Data
@ApiModel
public class GetTokenReqDto {

    private static final long serialVersionUID = 3967075132487249652L;

    /**
     * 签名原Hex
     */
    @ApiModelProperty(value = "签名原Hex")
    private String dataHex;
    /**
     * 公钥
     */
    @ApiModelProperty(value = "公钥")
    private String publicKeyHex;

    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    private String signatureHex;

}
