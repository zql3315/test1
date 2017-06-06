package com.infosky.common.query.jpa;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月27日]
 */
public class Request {

    private String fieldName;

    private Operator operator;

    private Object value;

    /**
     * @return 返回 fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param 对fieldName进行赋值
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return 返回 operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @param 对operator进行赋值
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * @return 返回 value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param 对value进行赋值
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
