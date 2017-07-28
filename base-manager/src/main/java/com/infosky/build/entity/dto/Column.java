package com.infosky.build.entity.dto;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Column {

    //db
    private String columnName;

    private int length;

    private String type;

    private int digits;

    private int nullable;

    private String remarks;

    //entity
    private String propertyName;

    private String setter;

    private String getter;

    private String javaType;

    /**
     * @return 返回 length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param 对length进行赋值
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 返回 digits
     */
    public int getDigits() {
        return digits;
    }

    /**
     * @param 对digits进行赋值
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * @return 返回 nullable
     */
    public int getNullable() {
        return nullable;
    }

    /**
     * @param 对nullable进行赋值
     */
    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    /**
     * @return 返回 setter
     */
    public String getSetter() {
        return setter;
    }

    /**
     * @param 对setter进行赋值
     */
    public void setSetter(String setter) {
        this.setter = setter;
    }

    /**
     * @return 返回 getter
     */
    public String getGetter() {
        return getter;
    }

    /**
     * @param 对getter进行赋值
     */
    public void setGetter(String getter) {
        this.getter = getter;
    }

    /**
     * @return 返回 javaType
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * @param 对javaType进行赋值
     */
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    /**
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return 返回 propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param 对propertyName进行赋值
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return 返回 remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param 对remarks进行赋值
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
