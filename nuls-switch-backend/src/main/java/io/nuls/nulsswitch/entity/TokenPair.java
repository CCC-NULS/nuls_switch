package io.nuls.nulsswitch.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
@Data
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
}
