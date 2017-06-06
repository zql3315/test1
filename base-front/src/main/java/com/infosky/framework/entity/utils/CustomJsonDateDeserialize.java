package com.infosky.framework.entity.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.date.DateUtilsExtend;

/**
 * 绑定数据反序列化<br>
 * 
 * 支持json类型的数据从string -> date
 * 
 * 支持多种格式的时间类型，可以扩展DateUtils.parsePatterns数组来支持更多的时间类型
 * 
 * @date 2016-08-30
 * 
 * @author n004881
 */
public class CustomJsonDateDeserialize extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException, JsonProcessingException {
        if (!StringUtils.hasLength(paramJsonParser.getText())) {
            return null;
        }
        String str = paramJsonParser.getText().trim();
        try {
            if (CommonUtil.IsNumeric(str))
                return new Date(Long.parseLong(str));
            else
                return DateUtilsExtend.parseDate(str, DateUtilsExtend.parsePatterns);
        } catch (ParseException e) {

        }
        return paramDeserializationContext.parseDate(str);
    }
}
