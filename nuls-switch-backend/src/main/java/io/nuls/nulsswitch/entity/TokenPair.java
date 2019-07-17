package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 代币兑换交易对
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@TableName("tx_token_pair")
public class TokenPair implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易对ID
     */
    @TableId(value = "pair_id", type = IdType.AUTO)
    private Integer pairId;
    /**
     * 原token
     */
    @TableField("from_token_id")
    private Integer fromTokenId;
    /**
     * 转换为目标token
     */
    @TableField("to_token_id")
    private Integer toTokenId;
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


    public Integer getPairId() {
        return pairId;
    }

    public void setPairId(Integer pairId) {
        this.pairId = pairId;
    }

    public Integer getFromTokenId() {
        return fromTokenId;
    }

    public void setFromTokenId(Integer fromTokenId) {
        this.fromTokenId = fromTokenId;
    }

    public Integer getToTokenId() {
        return toTokenId;
    }

    public void setToTokenId(Integer toTokenId) {
        this.toTokenId = toTokenId;
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
        return "TokenPair{" +
        ", pairId=" + pairId +
        ", fromTokenId=" + fromTokenId +
        ", toTokenId=" + toTokenId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
