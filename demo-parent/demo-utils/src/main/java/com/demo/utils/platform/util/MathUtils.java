package com.demo.utils.platform.util;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;


/**
 * @author work2
 */
public class MathUtils {

    private static final int DEF_DIV_SCALE = 4;


    public static String add(String v1, String v2) {

        if (v1 == null || ("").equals(v1)) {
            v1 = "0";
        }
        if (v2 == null || ("").equals(v2)) {
            v2 = "0";
        }

        BigDecimal b1 = new BigDecimal(v1);

        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).toString();

    }

    public static String sub(String v1, String v2) {
        if (v1 == null || ("").equals(v1)) {
            v1 = "0";
        }
        if (v2 == null || ("").equals(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    public static String mul(String v1, String v2) {
        if (v1 == null || ("").equals(v1)) {
            v1 = "0";
        }
        if (v2 == null || ("").equals(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }


    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }


    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (v1 == null || ("").equals(v1)) {
            v1 = "0";
        }
        if (v2 == null || ("").equals(v2)) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    public static String round(String v, int scale) {
        if (v == null || ("").equals(v)) {
            v = "0";
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    public static String roundTo10(String v, int scale) {
        BigDecimal b = new BigDecimal(v);
        b.add(new BigDecimal("4"));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static String roundTo5(String v, int scale) {
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public static String roundTo5Type(String v, int scale) {
        BigDecimal b = new BigDecimal(v);
        if (b.intValue() >= 5) {
            b = b.subtract(new BigDecimal("5"));
        } else {
            b = new BigDecimal(0);
        }
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 把元转成分
     *
     * @param tradeAmount
     * @return
     */
    public static String changeToCent(String tradeAmount) {
        if (!StringUtils.hasText(tradeAmount)) {
            return "";
        }
        BigDecimal amt = new BigDecimal(tradeAmount);
        BigDecimal divide = new BigDecimal("100");
        return amt.multiply(divide).setScale(0).toString();
    }

};
