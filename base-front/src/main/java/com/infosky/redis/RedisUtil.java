package com.infosky.redis;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * redis工具类
 * 
 * @author n004881
 */
@Repository("redisUtil")
public class RedisUtil {

    public static Logger logger = Logger.getLogger(RedisUtil.class);

    @Autowired
    private JedisPool jedisPool;

    public void hset(String k, String v) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(k, v);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("save", e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public void hset(String id, String k, String v) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(id, k, v);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }

    }

    public String setex(byte[] key, int seconds, byte[] value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public String setex(String key, int seconds, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public void save(String id, Map kv) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(id, kv);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveAllKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }

    }

    public void hdel(String id, String k) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(id, k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("clearAllKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public String get(String k) {
        String v = "";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            v = jedis.get(k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("get", e);

        } finally {
            if (jedis != null) jedis.close();
        }
        return v;
    }

    public byte[] get(byte[] k) {
        byte[] v = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            v = jedis.get(k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("get", e);

        } finally {
            if (jedis != null) jedis.close();
        }
        return v;
    }

    public String hget(String id, String k) {
        String v = "";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            v = jedis.hget(id, k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return v;
    }

    public Set<String> hkeys(String k) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hkeys(k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return null;
    }

    public Map<String, String> hgetAll(String id) {

        Map<String, String> v = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            v = jedis.hgetAll(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getAllKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return v;
    }

    public void del(String id) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("clear", e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

}
