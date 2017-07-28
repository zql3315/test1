package com.infosky.common.util.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * redis 连接池工具类
 * @author n004881
 *
 */
public class JedisPoolUtils {

    private static JedisPool pool;

    /**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     * 
     */
    private static void createJedisPool() {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        //		config.setMaxActive(100);

        // 设置最大阻塞时间，记住是毫秒数milliseconds
        //		config.setMaxWait(1000);

        // 设置空间连接
        config.setMaxIdle(10);

        config.setMaxTotal(100);

        // 创建连接池
        pool = new JedisPool(config, "127.0.0.1", 6379);

    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (pool == null) createJedisPool();
    }

    /**
     * 获取一个jedis 对象
     * 
     * @return
     */
    public static Jedis getJedis() {

        if (pool == null) poolInit();
        return pool.getResource();
    }

    /**
     * 归还一个连接
     * 
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 创建分片对象
     * 
     * @return
     */
    private static ShardedJedis createShardJedis() {

        // 建立服务器列表
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

        // 添加第一台服务器信息
        JedisShardInfo si = new JedisShardInfo("localhost", 6379);
        si.setPassword("123");
        shards.add(si);

        // 添加第二台服务器信息
        si = new JedisShardInfo("localhost", 6399);
        si.setPassword("123");
        shards.add(si);
        // 建立分片连接对象
        ShardedJedis jedis = new ShardedJedis(shards);

        // 建立分片连接对象,并指定Hash算法
        // ShardedJedis jedis = new ShardedJedis(shards,selfHash);
        return jedis;
    }

    //	分片也可以支持连接池
    private static void createPool() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("localhost", 6379);
        si.setPassword("123");
        shards.add(si);
        si = new JedisShardInfo("localhost", 6399);
        si.setPassword("123");
        shards.add(si);
        //		pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    }
}
