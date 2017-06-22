package com.infosky.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.Request;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;


/**
 * 动态查询工具类
 * 
 * @author n004881
 * @version [版本号, 2015年3月17日]
 */
public abstract class DynamicSearchUtils {

    private static final Logger logger = LoggerFactory.getLogger(DynamicSearchUtils.class);

    private static final String REQUEST_SEARCH_PREFIX = "search_";

    private static final String SHORT_DATE = "yyyy-MM-dd";

    private static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME = "HH:mm:ss";

    /**
     * 分解request请求，转行成相应的查询条件
     * 
     * @param request
     * @return 返回查询参数封装的对象 Searchable
     */
    public static Searchable toSearchable(ServletRequest request) {
        Validate.notNull(request, "Request must not be null");

        Searchable searchrequest = new SearchRequest();

        Enumeration<String> paramNames = request.getParameterNames();
        // 遍历request中的参数名
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            // 参数是否search_开头
            if (paramName.startsWith(REQUEST_SEARCH_PREFIX)) {
                String unprefixed = paramName.substring(REQUEST_SEARCH_PREFIX.length());
                // 分离操作符与字段名
                String operator = unprefixed.substring(0, unprefixed.indexOf("_"));
                String fieldName = unprefixed.substring(unprefixed.indexOf("_") + 1);

                String[] values = request.getParameterValues(paramName);

                if ((values == null) || (values.length == 0)) {
                    // Do nothing, no values found at all.
                    logger.info("Do nothing, no values found at all");
                } else if (values.length > 1) {
                    searchrequest.addSearchParam(fieldName, Operator.valueOf(operator), values);
                } else if (StringUtils.isNotBlank(values[0])) {
                    searchrequest.addSearchParam(fieldName, Operator.valueOf(operator), values[0].trim());
                }
            }
        }
        Enumeration<String> attributeNames = request.getAttributeNames();
        // 遍历attribute中的参数名
        while ((attributeNames != null) && attributeNames.hasMoreElements()) {
            String paramName = (String) attributeNames.nextElement();
            // 参数是否search_开头
            if (paramName.startsWith(REQUEST_SEARCH_PREFIX)) {
                String unprefixed = paramName.substring(REQUEST_SEARCH_PREFIX.length());
                // 分离操作符与字段名
                String operator = unprefixed.substring(0, unprefixed.indexOf("_"));
                String fieldName = unprefixed.substring(unprefixed.indexOf("_") + 1);

                Object value = request.getAttribute(paramName);

                if (value == null) {
                    logger.info("Do nothing, no values found at all");
                } else if (StringUtils.isNotBlank(value.toString())) {
                    searchrequest.addSearchParam(fieldName, Operator.valueOf(operator), value.toString().trim());
                }
            }
        }

        return searchrequest;
    }

    /**
     * 把查询对象Searchable转换成spring data jpa的查询对象Specification
     * 
     * @param searchable
     * @return 返回 Specification 对象
     */
    public static <T> Specification<T> toSpecification(final Searchable searchable) {
        Specification<T> specification = new Specification<T>() {

            @SuppressWarnings({
                    "unchecked", "rawtypes"
            })
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (null == searchable) {
                    return builder.conjunction();
                }

                List<Request> searchRequests = searchable.getConditions();

                List<Predicate> predicates = new ArrayList<Predicate>();

                for (Request request : searchRequests) {
                    // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                    String[] names = StringUtils.split(request.getFieldName(), ".");
                    Path expression = root.get(names[0]);
                    for (int i = 1; i < names.length; i++) {
                        expression = expression.get(names[i]);
                    }

                    // 自动进行enum和date的转换。
                    Class clazz = expression.getJavaType();
                    if (Date.class.isAssignableFrom(clazz) && !request.getValue().getClass().equals(clazz)) {
                        request.setValue(convert2Date((String) request.getValue()));
                    }

                    // logic operator
                    switch (request.getOperator()) {
                        case ISNULL:
                            predicates.add(builder.isNull(expression));
                            break;
                        case ISNOTNULL:
                            predicates.add(builder.isNotNull(expression));
                            break;
                        case EQ:
                            predicates.add(builder.equal(expression, request.getValue()));
                            break;
                        case NEQ:
                            predicates.add(builder.notEqual(expression, request.getValue()));
                            break;
                        case LIKE:
                            predicates.add(builder.like(expression, "%" + request.getValue() + "%"));
                            break;
                        case PRELIKE:
                            predicates.add(builder.like(expression, request.getValue() + "%"));
                            break;
                        case SUFLIKE:
                            predicates.add(builder.like(expression, "%" + request.getValue()));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(expression, (Comparable) request.getValue()));
                            break;
                        case LT:
                            predicates.add(builder.lessThan(expression, (Comparable) request.getValue()));
                            break;
                        case GTE:
                            predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) request.getValue()));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) request.getValue()));
                            break;
                        case IN:
                            predicates.add(builder.and(expression.in((Object[]) request.getValue())));
                            break;
                    }
                }

                // 将所有条件用 and 联合起来
                if (predicates.size() > 0) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    // //把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的
                    // query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
                    // //添加排序的功能
                    // query.orderBy(builder.desc(root.get("id").as(String.class)));
                    // return query.getRestriction();
                }
                return builder.conjunction();
            }

        };

        return specification;
    }

    private static Date convert2Date(String dateString) {
        String pattern = null;

        if (dateString.length() == 19) {
            pattern = LONG_DATE;
        } else if (dateString.length() == 10) {
            pattern = SHORT_DATE;
        } else {
            pattern = TIME;
        }

        SimpleDateFormat sFormat = new SimpleDateFormat(pattern);

        try {
            return sFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("Convert time is error!", e);
        }

        return null;
    }

    /**
     * 获取动态sql
     * 
     * @param params
     *            参数
     * @param prefix
     *            前缀
     * @param sql
     * @return
     */
    public static String toDynamicSql(List<Request> params, StringBuffer sql) {
        sql.insert(0, " select * from ( ");
        sql.append(" ) v_ where 1=1 ");
        for (int i = 0; i < params.size(); i++) {
            Request request = params.get(i);
            switch (request.getOperator()) {
                case ISNULL:
                    sql.append(" and v_." + request.getFieldName() + " is null ");
                    break;
                case ISNOTNULL:
                    sql.append(" and v_." + request.getFieldName() + " is not null ");
                    break;
                case EQ:
                    sql.append(" and v_." + request.getFieldName() + " = ? ");
                    break;
                case NEQ:
                    sql.append(" and v_." + request.getFieldName() + " <> ? ");
                    break;
                case LIKE:
                    sql.append(" and v_." + request.getFieldName() + " like ? ");
                    break;
                case PRELIKE:
                    sql.append(" and v_." + request.getFieldName() + " like ? ");
                    break;
                case SUFLIKE:
                    sql.append(" and v_." + request.getFieldName() + " like ? ");
                    break;
                case GT:
                    sql.append(" and v_." + request.getFieldName() + "  > ? ");
                    break;
                case LT:
                    sql.append(" and v_." + request.getFieldName() + " < ? ");
                    break;
                case GTE:
                    sql.append(" and v_." + request.getFieldName() + " >= ? ");
                    break;
                case LTE:
                    sql.append(" and v_." + request.getFieldName() + " <= ? ");
                    break;
                case IN:
                    sql.append(" and v_." + request.getFieldName() + " in (?) ");
                    break;
                default:
                    break;
            }
        }
        return sql.toString();
    }

    /**
     * 设置动态查询
     * 
     * @param params
     */
    public static void toDynamicQuery(Query query, List<Request> params) {
        for (int i = 0; i < params.size(); i++) {
            Request request = params.get(i);
            switch (request.getOperator()) {
                case LIKE:
                    query.setParameter(i + 1, "%" + request.getValue() + "%");
                    break;
                case PRELIKE:
                    query.setParameter(i + 1, request.getValue() + "%");
                    break;
                case SUFLIKE:
                    query.setParameter(i + 1, "%" + request.getValue());
                    break;
                case ISNULL:
                    break;
                case ISNOTNULL:
                    break;
                default:
                    query.setParameter(i + 1, request.getValue());
                    break;
            }
        }
    }
}
