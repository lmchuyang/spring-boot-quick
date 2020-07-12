/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.tool;

import java.text.ParseException;

public class NumberFormat {

    public static String getDoubleValue(String val) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        // 不使用千分位，即展示为11672283.234，而不是11,672,283.234
        nf.setGroupingUsed(false);
        // 设置数的小数部分所允许的最小位数
        nf.setMinimumFractionDigits(0);
        // 设置数的小数部分所允许的最大位数
        nf.setMaximumFractionDigits(5);
        String value = null;
        try {
            value = nf.format(nf.parse(val).doubleValue());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
}
