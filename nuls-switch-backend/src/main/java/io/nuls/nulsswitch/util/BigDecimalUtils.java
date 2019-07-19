package io.nuls.nulsswitch.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 类描述：
 *
 * @author qinyifeng
 * @version v1.0
 * @date 2019/7/19 18:09
 */
public class BigDecimalUtils {

    public static int scale = 8;

    public static double add(double dou1, double dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.add(big2).setScale(BigDecimalUtils.scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double subtract(double dou1, double dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.subtract(big2).setScale(BigDecimalUtils.scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param dou1
     * @param dou2
     * @return
     */
    public static double mul(String dou1, String dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.multiply(big2).doubleValue();
    }

    public static double mul(double dou1, double dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.multiply(big2).setScale(BigDecimalUtils.scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal mul(BigDecimal param1, BigDecimal param2) {
        return param1.multiply(param2).setScale(BigDecimalUtils.scale, BigDecimal.ROUND_HALF_UP);
    }

    public static double mul(double dou1, double dou2, int scale) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.multiply(big2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double divide(double dou1, double dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.divide(big2, BigDecimalUtils.scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double divide(double dou1, double dou2, int scale) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.divide(big2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double divide(String dou1, String dou2) {
        BigDecimal big1 = new BigDecimal(dou1);
        BigDecimal big2 = new BigDecimal(dou2);
        return big1.divide(big2, BigDecimalUtils.scale, RoundingMode.HALF_UP).doubleValue();
    }
}
