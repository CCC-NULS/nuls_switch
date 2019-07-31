package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 挂单交易明细，一个挂单可以对应多个明细记录
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@TableName("tx_trade")
@Data
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    @TableId("tx_id")
    private String txId;
    /**
     * 委托挂单ID
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 交易用户地址
     */
    private String address;
    /**
     * 原token已完成交易数量
     */
    @TableField("tx_num")
    private Long txNum;

    /**
     * 目标token已完成交易数量，通过价格换算得来
     */
    @TableField("to_num")
    private Long toNum;
    /**
     * 交易hash
     */
    @TableField("tx_hash")
    private String txHash;

    /**
     * 状态：0-交易未确认、1-交易确认中、2-交易完成确认、9-撤销
     */
    @TableField("status")
    private Integer status;
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
