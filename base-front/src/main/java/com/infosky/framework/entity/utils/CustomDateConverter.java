package com.infosky.framework.entity.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.date.DateUtilsExtend;

/**
 * 表单绑定参数转换器
 * 
 * string -> date
 * 
 * @author n004881
 */
public class CustomDateConverter implements Converter<String, Date> {

    private Set<String> parsePatterns;

    @Override
    public Date convert(String source) {
        if (!StringUtils.hasLength(source)) {
            return null;
        }
        try {
            if (CommonUtil.IsNumeric(source)) return new Date(Long.parseLong(source));
            return DateUtilsExtend.parseDate(source, (String[]) parsePatterns.toArray(new String[parsePatterns.size()]));
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式%s，但格式是[%s]", DateUtilsExtend.parsePatterns, source));
        }
    }

    public Set<String> getParsePatterns() {
        return parsePatterns;
    }

    public void setParsePatterns(Set<String> parsePatterns) {
        this.parsePatterns = parsePatterns;
    }

}
