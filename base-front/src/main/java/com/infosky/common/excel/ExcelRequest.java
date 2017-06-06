package com.infosky.common.excel;

import java.util.Map;

/**
 * Excel导入导出功能
 * 
 * @author  n004881
 */
public class ExcelRequest {

    /**
     * 导入导出指定解析模板
     */
    private String template;

    private int pageSize;

    private int position;

    private String suffix;

    private Map<String, Object> extInfo;

    /**
     * @return 返回 template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param 对template进行赋值
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return 返回 pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param 对pageSize进行赋值
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return 返回 position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param 对position进行赋值
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return 返回 suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param 对suffix进行赋值
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

}
