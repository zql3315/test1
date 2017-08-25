package com.infosky.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 状态举类
 * @author 004881
 */
public enum StatusEnum {
    SUCCESS(200, "成功", "Success"), ERROR(500, "失败", "Error"), ;

    /**枚举值 */
    private int value;

    /**中文名称 */
    private String chDescription;

    /**英文名称 */
    private String enDescription;

    StatusEnum(int value, String chDescription, String enDescription) {
        this.value = value;
        this.chDescription = chDescription;
        this.enDescription = enDescription;
    }

    public int getValue() {
        return value;
    }

    public String getChDescription() {
        return chDescription;
    }

    public String getEnDescription() {
        return enDescription;
    }

    /**
     * @Desc : 值与中文名称映射列表，用于界面的下拉选项框
     *      LinkedHashMap: 保证输出和输入的顺序相同
     */
    public static final Map<Integer, String> STATUSMAP = new LinkedHashMap<Integer, String>();
    static {
        for (StatusEnum enumObj : StatusEnum.values()) {
            STATUSMAP.put(enumObj.getValue(), enumObj.getChDescription());
        }
    }

    /**
     * @Desc : 值与英文名称映射列表，用于界面的下拉选项框
     */
    public static final Map<Integer, String> STATUSMAP_EN = new LinkedHashMap<Integer, String>();
    static {
        for (StatusEnum enumObj : StatusEnum.values()) {
            STATUSMAP_EN.put(enumObj.getValue(), enumObj.getEnDescription());
        }
    }
}
