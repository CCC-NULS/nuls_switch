package io.nuls.nulsswitch.web.vo.token;

import com.baomidou.mybatisplus.annotations.TableField;
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
     * 代币类型：1:Nuls,2:跨链资产,3:NRC20资产
     */
    @ApiModelProperty(value = "代币类型")
    private Integer tokenType;

    /**
     * 资产链ID
     */
    @ApiModelProperty(value = "资产链ID")
    private Integer chainId;

    /**
     * 跨链资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private Integer assetId;

    /**
     * 智能合约地址
     */
    @ApiModelProperty(value = "智能合约地址")
    private String contractAddress;

    /**
     * 精度，防止存储数据为小数
     */
    @ApiModelProperty(value = "精度")
    private Integer decimals;
}
