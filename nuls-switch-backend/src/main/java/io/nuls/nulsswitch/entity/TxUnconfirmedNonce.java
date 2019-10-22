package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 本地未确认交易nonce，用于连续交易
 * </p>
 *
 * @author qinyifeng
 * @since 2019-10-21
 */
@TableName("tx_unconfirmed_nonce")
@Data
public class TxUnconfirmedNonce implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * nonce唯一标识
     */
    @TableId(value = "nonce_id", type = IdType.AUTO)
    private Integer nonceId;

    /**
     * 交易地址
     */
    @TableField("address")
    private String address;

    /**
     * 交易hash
     */
    @TableField("tx_hash")
    private String txHash;

    /**
     * 交易未确认nonce
     */
    @TableField("nonce")
    private String nonce;

    /**
     * 资产链ID
     */
    @TableField("chain_id")
    private Integer chainId;

    /**
     * 链资产ID
     */
    @TableField("asset_id")
    private Integer assetId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
