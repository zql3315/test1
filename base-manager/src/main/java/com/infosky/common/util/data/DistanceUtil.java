package com.infosky.common.util.data;

import java.math.BigDecimal;

/**
 * 距离计算工具类
 * 
 * @author n004881
 *
 */
public class DistanceUtil {

    public static void main(String[] args) {
        double s1 = GetDistance(118.751520, 31.974100, 118.744190, 31.963210);
        System.out.println(s1);

    }

    /**
     * 地球半径
     */
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     *  计算地球上任意两点(GPS的经纬度)距离 
     * 
     * @param lng1 第一点经度
     * @param lat1 第一点维度
     * @param lng2 第二点经度
     * @param lat2 第二点维度
     * @return 返回距离 单位：千米
     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return s * EARTH_RADIUS;
    }

    /**
     *  计算地球上任意两点(GPS的经纬度)距离 
     * 
     * @param lng1 第一点经度
     * @param lat1 第一点维度
     * @param lng2 第二点经度
     * @param lat2 第二点维度
     * @param scale 取几位小数点
     * 
     * @return 返回距离 单位：千米
     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2, int scale) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        BigDecimal bg = new BigDecimal(s);
        return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
