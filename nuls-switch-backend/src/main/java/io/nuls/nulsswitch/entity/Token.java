package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支持交易的代币
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@TableName("tx_token")
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代币唯一标识
     */
    @TableId(value = "token_id", type = IdType.AUTO)
    private Integer tokenId;

    /**
     * 代币类型：1:Nuls,2:跨链资产,3:NRC20资产
     */
    @TableField("token_type")
    private Integer tokenType;

    /**
     * 资产链ID
     */
    @TableField("chain_id")
    private Integer chainId;

    /**
     * 跨链资产ID
     */
    @TableField("asset_id")
    private Integer assetId;

    /**
     * 智能合约地址
     */
    @TableField("contract_address")
    private String contractAddress;

    /**
     * 代币符号,例如:NULS
     */
    @TableField("token_symbol")
    private String tokenSymbol;

    /**
     * 代币中文名称,例如:纳世币
     */
    @TableField("token_name")
    private String tokenName;

    /**
     * 精度，防止存储数据为小数
     */
    @TableField("decimals")
    private Integer decimals;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
