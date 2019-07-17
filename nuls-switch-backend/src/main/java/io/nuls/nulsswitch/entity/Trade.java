package io.nuls.nulsswitch.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

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
    private Integer orderId;
    /**
     * 交易用户地址
     */
    private String address;
    /**
     * 交易数量
     */
    @TableField("tx_num")
    private Integer txNum;
    /**
     * 状态：0-未交易、1-完成交易、9-撤销
     */
    private String status;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTxNum() {
        return txNum;
    }

    public void setTxNum(Integer txNum) {
        this.txNum = txNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
