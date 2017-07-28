package com.infosky.redis.shard;

import redis.clients.jedis.ShardedJedis;

/**
 * redis 客户端接口
 * 
 * @author n004881
 */
public interface RedisDataSource {

    /**
     * 取得redis的客户端
     * 
     * @return
     */
    public abstract ShardedJedis getRedisClient();

    /**
     * 将资源返还给pool
     * 
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);

    /**
     * 出现异常后，将资源返还给pool （其实不需要第二个方法）
     * 
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
