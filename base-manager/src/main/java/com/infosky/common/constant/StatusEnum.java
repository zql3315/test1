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
     * @author HZ
     * @create: 2015-10-26 上午11:52:08
     */
    public static final Map<Integer, String> SEASONSMAP = new LinkedHashMap<Integer, String>();
    static {
        for (StatusEnum enumObj : StatusEnum.values()) {
            SEASONSMAP.put(enumObj.getValue(), enumObj.getChDescription());
        }
    }

    /**
     * @Desc : 值与英文名称映射列表，用于界面的下拉选项框
     * @author HZ
     * @create: 2015-10-26 上午11:53:24
     */
    public static final Map<Integer, String> SEASONSMAP_EN = new LinkedHashMap<Integer, String>();
    static {
        for (StatusEnum enumObj : StatusEnum.values()) {
            SEASONSMAP_EN.put(enumObj.getValue(), enumObj.getEnDescription());
        }
    }
}
