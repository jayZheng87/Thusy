package com.rambo.tools;

import java.math.BigDecimal;

public class NumberUtil extends org.apache.commons.lang.math.NumberUtils {
    /**
     * Description:转换科学计数法
     */
    public static String convertSCM(String str) {
        if (!"".equals(str) && str.contains("E")) {
            BigDecimal db = new BigDecimal(str);
            str = db.toPlainString();
        }
        return str;
    }

    public static double convertSCM(Double dou) {
        return Double.parseDouble(convertSCM(dou.toString()));
    }
}