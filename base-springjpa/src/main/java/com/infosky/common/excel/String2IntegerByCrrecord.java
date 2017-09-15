package com.infosky.common.excel;

public class String2IntegerByCrrecord {

    public Integer convert(Object targetValue) {
        if (targetValue != null) {
            if (targetValue.equals("是")) {
                return 1;
            } else if (targetValue.equals("否")) {
                return 0;
            }
        }

        return 0;

    }
}
