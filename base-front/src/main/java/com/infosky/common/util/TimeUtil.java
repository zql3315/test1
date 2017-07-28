package com.infosky.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class TimeUtil {

    public static String currentTime(String pattern) {
        if (StringUtils.isBlank(pattern)) pattern = "yyyyMMddHHmmss";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 把时间字符串变成data类型
     * @param pattern
     * @return
     * @throws ParseException 
     */
    public static Date formatStringTime(String source, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(source);
    }

    /**
     * Date类型转成XMLGregorianCalendar类型
     * @param date
     * @return
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    /**
     * XMLGregorianCalendar类型转成Date类型
     * @param XMLGregorianCalendar
     * @return
     */
    public static Date convertToDate(XMLGregorianCalendar cal) {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * 获取系统当前时间 14位
     * @return
     */
    public static String getSystemTime() {
        String time = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        time = format.format(date);
        return time;
    }

    /**
     * 转换时间显示格式
     * 14位转换成19位页面显示
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        if (time != null && time.trim().length() == 14) {
            time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
        }
        return time;
    }

    /**
     * 过滤时间字符串中的特殊字符
     * @param time
     * @return
     * @throws ParseException 
     */
    public static String filterTime(String time) {
        if (time != null) {
            time = time.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").trim();
        }
        return time;
    }

}
