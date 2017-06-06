package com.infosky.common.excel;

public class Double2Integer {

    public Integer convert(Object targetValue) {
        if (targetValue != null) {
            return Integer.parseInt(new java.text.DecimalFormat("0").format(targetValue));
        }

        return -1;
    }
}
