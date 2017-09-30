package com.infosky.redis.shard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * redis2.7+ 分片式实现类
 * 
 * @author n004881
 */
public class RedisDataSourceImpl implements RedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    private ShardedJedisPool shardedJedisPool;

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    /**
     * //TODO 添加override说明
     * @see com.infosky.redis.RedisDataSource#getRedisClient()
     **/
    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getRedisClent error ", e);
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        if (shardedJedis != null) shardedJedis.close();
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (shardedJedis != null) shardedJedis.close();
    }
}
