package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

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
     * 交易数量
     */
    @TableField("tx_num")
    private Long txNum;

    /**
     * 交易hash
     */
    @TableField("tx_hash")
    private Integer txHash;

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


    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTxNum() {
        return txNum;
    }

    public void setTxNum(Long txNum) {
        this.txNum = txNum;
    }

    public Integer getTxHash() {
        return txHash;
    }

    public void setTxHash(Integer txHash) {
        this.txHash = txHash;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Trade{" +
                ", txId=" + txId +
                ", orderId=" + orderId +
                ", address=" + address +
                ", txNum=" + txNum +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
