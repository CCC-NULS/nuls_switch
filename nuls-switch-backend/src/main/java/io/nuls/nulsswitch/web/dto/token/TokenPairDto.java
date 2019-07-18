package io.nuls.nulsswitch.web.dto.token;

import io.nuls.nulsswitch.entity.Token;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：支持兑换的代币交易对
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/18 15:18
 */
@EqualsAndHashCode
@Data
@ApiModel
public class TokenPairDto implements Serializable {

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

    /**
     * 可兑换代币
     */
    @ApiModelProperty(value = "可兑换代币")
    private List<TokenDto> switchTokenList;
}