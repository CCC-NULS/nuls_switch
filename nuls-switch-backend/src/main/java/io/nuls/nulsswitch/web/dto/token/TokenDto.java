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