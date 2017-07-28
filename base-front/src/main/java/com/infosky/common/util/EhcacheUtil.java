package com.infosky.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache 缓存工具类 cacheName在ehcache.xml中配置
 * 
 * @author n004881
 * 
 */
public class EhcacheUtil {

    public static String configfile = "/ehcache/ehcache.xml";

    public static CacheManager manager;

    static {
        manager = CacheManager.create(EhcacheUtil.class.getResource(configfile));
    }

    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @param key 缓存key
     * @return
     */
    public static Object get(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        }
        return null;
    }

    /**
     * 设置缓存对象
     * @param cacheName 缓存名称
     * @param key  缓存key
     * @param value 缓存值
     */
    public static void put(String cacheName, Object key, Object value) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.put(new Element(key, value));
        }
    }

    /**
     * 设置缓存对象(支持永久缓存到硬盘)
     * @param cacheName 缓存名称
     * @param key  缓存key
     * @param value 缓存值
     */
    public static void putDisk(String cacheName, Object key, Object value) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.put(new Element(key, value));
            cache.flush();
        }
    }

    /**
     * 删除缓存
     * @param cacheName 缓存名称
     * @param key 缓存key
     * @return
     */
    public static boolean remove(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache.remove(key);
        }
        return false;
    }

    public static void main(String[] args) {
        String key = "key";
        String value = "hello";
        //		EhcacheUtil.put("disktest", key, value);
        //		EhcacheUtil.put("disktest", key+"1", value+"1");
        try {
            System.out.println(EhcacheUtil.get("disktest", "1"));
            System.out.println(EhcacheUtil.get("disktest", key));
            System.out.println(EhcacheUtil.get("disktest", key + "1"));
            //			Thread.sleep(4000);
            //			System.out.println(EhcacheUtil.get("test", key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}