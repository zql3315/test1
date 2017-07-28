package com.infosky.common.query.jpa;

import java.util.List;

/**
 * 查询参数封装对象
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月17日]
 */
public interface Searchable {

    String SEARCHABLE_FIELDNAME = "fieldName";

    String SEARCHABLE_OPERATOR = "operator";

    String SEARCHABLE_VALUE = "value";

    void addSearchParam(String fieldName, Operator operator, Object value);

    List<Request> getConditions();

}
