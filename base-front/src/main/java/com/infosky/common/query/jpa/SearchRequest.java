package com.infosky.common.query.jpa;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <JPA请求封装类>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月17日]
 */
@XmlRootElement(name = "searchable")
public class SearchRequest implements Searchable {

    private List<Request> conditions = Lists.newArrayList();

    private Map<String, Object> ext = Maps.newHashMap();

    public void addSearchParam(String fieldName, Operator operator, Object value) {
        Request request = new Request();
        try {
            PropertyUtils.setProperty(request, SEARCHABLE_FIELDNAME, fieldName);
            PropertyUtils.setProperty(request, SEARCHABLE_OPERATOR, operator);
            PropertyUtils.setProperty(request, SEARCHABLE_VALUE, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conditions.add(request);
    }

    /**
     * @return 返回 conditions
     */
    @XmlElement(name = "parameter")
    public List<Request> getConditions() {
        return conditions;
    }

    /**
     * @param 对conditions进行赋值
     */
    public void setConditions(List<Request> conditions) {
        this.conditions = conditions;
    }

    /**
     * @return 返回 ext
     */
    public Map<String, Object> getExt() {
        return ext;
    }

    /**
     * @param 对ext进行赋值
     */
    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public Request getCondition(String param) {
        for (Request request : conditions) {
            if (request.getFieldName().equalsIgnoreCase(param)) {
                return request;
            }
        }

        return null;
    }

}
