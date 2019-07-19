package io.nuls.nulsswitch.constant;

/**
 * 类描述：常量类
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/18 21:23
 */
public class SwitchConstant {
    /**
     * 系统使用的编码方式
     * The encoding used by the nuls system.
     */
    public static String DEFAULT_ENCODING = "UTF-8";

    public static final String LANGUAGE = "en";
    public static final String LANGUAGE_PATH =  "languages";

    /** 订单相关 */
    /**
     * 订单类型：1-买入、2-卖出
     */
    public final static int TX_TYPE_BUY = 1;
    public final static int TX_TYPE_SELL = 2;

    /**
     * 状态：0-未交易、1-部分交易、2-完成交易、9-撤销
     */
    public final static int TX_ORDER_STATUS_INIT = 0;
    public final static int TX_ORDER_STATUS_PART = 1;
    public final static int TX_ORDER_STATUS_DONE = 2;
    public final static int TX_ORDER_STATUS_CANCEL = 9;
}
