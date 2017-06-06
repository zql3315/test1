package com.infosky.common.excel;

public class String2Integer {

    public Integer convert(Object targetValue) {
        if (targetValue != null) {
            return Integer.parseInt(targetValue.toString());
        }

        return -1;

    }
}
