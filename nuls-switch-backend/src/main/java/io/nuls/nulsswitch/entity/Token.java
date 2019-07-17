package io.nuls.nulsswitch.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 支持交易的代币
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@TableName("tx_token")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代币唯一标识
     */
    @TableField("token_id")
    private Integer tokenId;
    /**
     * 代币中文名称,例如:NULS
     */
    @TableField("token_name")
    private String tokenName;
    /**
     * 代币名称,例如:纳世币
     */
    @TableField("token_symbol")
    private String tokenSymbol;
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


    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
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
        return "Token{" +
        ", tokenId=" + tokenId +
        ", tokenName=" + tokenName +
        ", tokenSymbol=" + tokenSymbol +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
