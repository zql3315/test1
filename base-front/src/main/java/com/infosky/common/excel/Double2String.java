package com.infosky.common.excel;

public class Double2String {

    public String convert(Object targetValue) {
        if (targetValue != null) {
            return String.valueOf(new java.text.DecimalFormat("0").format(targetValue));
        }

        return "";
    }
}
