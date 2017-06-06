package com.infosky.redis.jedis;

import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.infosky.redis.service.AbsRedisUtilsServiceAdapter;
import com.infosky.redis.util.RedisSerializeUtil;

/**
 * redis客户端模板类.
 * 
 * @author n004881
 */
public class JedisTemplate extends AbsRedisUtilsServiceAdapter {

    private Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个值
     * 
     * @param key
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("设置失效失败.");
        } finally {
            getColse(jedis);
        }
        return null;
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

    /**
     * 获取对象
     * 
     * @param key
     */
    public Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return RedisSerializeUtil.unserialize(jedis.get(key.getBytes()));
        } catch (Exception e) {
            logger.error("设置失效失败.");
        } finally {
            getColse(jedis);
        }
        return null;
    }

    /**
     * @Description:设置失效时间
     * @param @param key
     * @param @param seconds
     * @param @return 
     * @return boolean 返回类型
     */
    public void disableTime(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("设置失效失败.");
        } finally {
            getColse(jedis);
        }
    }

    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            logger.error("插入数据有异常.");
        } finally {
            getColse(jedis);
        }
        return null;
    }

    /**
     * @Description:存储key~value
     * @param key
     * @param seconds 秒
     * @param value 
     * @return void 返回类型
     */

    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("插入数据有异常.");
        } finally {
            getColse(jedis);
        }
        return null;
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

    /**
     * 添加对象
     * 
      * @param key 键
     * @param value 保存的对象
     * @return
     */
    public boolean set(String key, Object value) {
        if (!(value instanceof Serializable)) {
            logger.error("保存的对象必须要要实现序列化接口");
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String code = jedis.set(key.getBytes(), RedisSerializeUtil.serialize(value));
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.error("插入数据有异常.");
        } finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * 保存对象对象
     * 
     * @param key 键
     * @param seconds 秒
     * @param value 保存的对象
     * @return
     */
    public boolean setexObject(String key, int seconds, Object value) {
        if (!(value instanceof Serializable)) {
            logger.error("保存的对象必须要要实现序列化接口");
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String code = jedis.setex(key.getBytes(), seconds, RedisSerializeUtil.serialize(value));
            if (code.equals("ok")) {
                return true;
            }

        } catch (Exception e) {
            logger.error("插入数据有异常.");
        } finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @Description:删除key
     * @param @param key
     * @param @return 
     * @return boolean 返回类型
     */
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            logger.error("删除key异常.");
        } finally {
            getColse(jedis);
        }
        return null;
    }

    public Long hdel(String id, String k) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(id, k);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("clearAllKey", e);
        } finally {
            if (jedis != null) jedis.close();
        }
        return null;
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

    /**
     * @Description: 关闭连接
     * @param @param jedis 
     * @return void 返回类型
     */

    public void getColse(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}