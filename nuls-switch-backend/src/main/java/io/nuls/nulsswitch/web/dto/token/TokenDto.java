package io.nuls.nulsswitch.web.dto.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述：交易对
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/18 15:18
 */
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TokenDto implements Serializable {

    /**
     * 代币ID
     */
    @ApiModelProperty(value = "代币ID")
    private Integer tokenId;

    /**
     * 代币符号
     */
    @ApiModelProperty(value = "代币符号")
    private String tokenSymbol;

    /**
     * 代币名称
     */
    @ApiModelProperty(value = "代币名称")
    private String tokenName;


}