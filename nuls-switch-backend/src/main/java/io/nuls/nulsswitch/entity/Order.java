package io.nuls.nulsswitch.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * <p>
 * 用户交易委托表：包括当前委托、历史委托
 * </p>
 *
 * @author Edward123
 * @since 2019-07-16
 */
@TableName("tx_order")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易单号
     */
    @TableId("order_id")
    private String orderId;
    /**
     * 挂单账户地址
     */
    private String address;
    /**
     * 订单类型：1-买入、2-卖出
     */
    @TableField("tx_type")
    private Integer txType;
    /**
     * 原token
     */
    @TableField("from_token_id")
    private Integer fromTokenId;
    /**
     * 目标token
     */
    @TableField("to_token_id")
    private Integer toTokenId;
    /**
     * 价格
     */
    private Long price;
    /**
     * 挂单总数量
     */
    @TableField("total_num")
    private Long totalNum;

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
     * 挂单总金额
     */
    @TableField("total_amount")
    private Long totalAmount;
    /**
     * 状态：0-未交易、1-部分交易、2-完成交易、9-撤销
     */
    @TableField("status")
    private Integer status;
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
