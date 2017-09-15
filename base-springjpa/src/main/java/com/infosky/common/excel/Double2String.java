package com.infosky.common.excel;

import org.apache.commons.lang.math.NumberUtils;

public class Double2String {

    public String convert(Object targetValue) {
        String str = targetValue.toString();
        if (targetValue != null) {
            if (targetValue instanceof String) {
                return str;
            } else if (NumberUtils.isNumber(str)) {//为数字
                return String.valueOf(new java.text.DecimalFormat("#").format(targetValue));
            }
        }
        return str;
    }

}
