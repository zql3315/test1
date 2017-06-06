package com.infosky.framework.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.date.DateUtilsExtend;

/**
 * 将网页传递过来的日期格式转换为java.util.Date
 * 默认支持转换格式:
 * yyyy-MM-dd
 * yyyy-MM-dd HH:mm:ss
 * 可根据需要扩展
 * 
 * @author n004881
 *
 */
public class DateEditor extends PropertyEditorSupport {

    private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateFormat dateFormat;

    private boolean allowEmpty = false;

    public DateEditor() {
    }

    public DateEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public DateEditor(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            try {
                if (this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
                else {
                    if (CommonUtil.IsNumeric(text)) {
                        setValue(new Date(Long.parseLong(text)));
                    } else {
                        setValue(DateUtilsExtend.parseDate(text, DateUtilsExtend.parsePatterns));
                    }
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        DateFormat dateFormat = this.dateFormat;
        if (dateFormat == null) dateFormat = TIMEFORMAT;
        return (value != null ? dateFormat.format(value) : "");
    }
}
