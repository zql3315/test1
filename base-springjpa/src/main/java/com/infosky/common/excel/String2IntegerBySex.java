package com.infosky.common.excel;

public class String2IntegerBySex {

    public Integer convert(Object targetValue) {
        if (targetValue != null) {
            if (targetValue.equals("女")) {
                return 0;
            } else if (targetValue.equals("男")) {
                return 1;
            }
        }

        return 1;

    }
}
