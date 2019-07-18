package io.nuls.nulsswitch.web.vo.token;

import io.nuls.nulsswitch.entity.TokenPair;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代币兑换交易对
 * </p>
 *
 * @author qinyifeng
 * @since 2019-07-18
 */
@EqualsAndHashCode
@Data
public class TokenPairVO extends TokenPair {

    private static final long serialVersionUID = 1L;

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
