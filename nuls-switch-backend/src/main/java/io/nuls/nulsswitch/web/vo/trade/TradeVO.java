package io.nuls.nulsswitch.web.vo.trade;

import io.nuls.nulsswitch.entity.Trade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 挂单交易明细页面视图映射
 * </p>
 *
 * @author qinyifeng
 * @since 2019-09-24
 */
@EqualsAndHashCode
@Data
public class TradeVO extends Trade {

    private static final long serialVersionUID = 1L;

    /**
     * 订单类型：1-买入、2-卖出，如果订单类型是买入，吃单交易页面展示就是卖出，二者相反
     */
    @ApiModelProperty(value = "订单类型")
    private Integer txType;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Long price;

    /**
     * 原token
     */
    @ApiModelProperty(value = "原tokenId")
    private Integer fromTokenId;
    /**
     * 目标token
     */
    @ApiModelProperty(value = "目标tokenId")
    private Integer toTokenId;

    /**
     * 原token名称
     */
    @ApiModelProperty(value = "原token名称")
    private String fromTokenName;
    /**
     * 目标token名称
     */
    @ApiModelProperty(value = "目标token名称")
    private String toTokenName;

    /**
     * 代币交易对
     */
    @ApiModelProperty(value = "代币交易对")
    private String tokenPair;

    @ApiModelProperty(value = "用户挂单地址")
    private String orderAddress;

    @ApiModelProperty(value = "资产的链ID")
    protected Integer assetsChainId;

    @ApiModelProperty(value = "资产ID")
    protected Integer assetsId;

    @ApiModelProperty(value = "资产的链ID")
    private Integer orderAssetsChainId;

    @ApiModelProperty(value = "资产ID")
    private Integer orderAssetsId;

    @ApiModelProperty(value = "资产的链ID")
    private Integer nulsChainId;

    @ApiModelProperty(value = "资产ID")
    private Integer nulsAssetsId;

//    @ApiModelProperty(value = "交易nonce")
//    private String nonce;
}
