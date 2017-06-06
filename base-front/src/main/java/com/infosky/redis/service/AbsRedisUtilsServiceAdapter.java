package com.infosky.redis.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * redis 工具类适配器
 * @author n004881
 *
 */
public abstract class AbsRedisUtilsServiceAdapter implements RedisUtilsService {

    /**
     * 添加.
     * 
     * @param key
     * @param value
     * @return
     */
    public Long append(byte[] key, byte[] value) {
        return null;
    };

    /**
     * 添加.
     * 
     * @param key
     * @param value
     * @return
     */
    public Long append(String key, String value) {
        return null;
    };

    /**
     * 将指定Key的值减少1.
     * 
     * @param key
     * @return
     */
    public Long decr(byte[] key) {
        return null;
    };

    /**
     * 将指定Key的值减少1.
     * 
     * @param key
     * @return
     */
    public Long decr(String key) {
        return null;
    };

    /**
     * 名称为key的string减少integer.
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(byte[] key, long integer) {
        return null;
    };

    /**
     * 名称为key的string减少integer.
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key, long integer) {
        return null;
    };

    /**
     * 删除一个key.
     * 
     * @param key
     * @return
     */
    public Long del(String key) {
        return null;
    };

    /**
     * 断开连接
     */
    public void disconnect() {

    };

    /**
     * 确认一个key是否存在.
     * 
     * @param key
     * @return
     */
    public Boolean exists(byte[] key) {
        return null;
    };

    /**
     * 判断是否存在.
     * 
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        return null;
    };

    /**
     * 为key设置一个特定的过期时间，单位为秒。过期时间一到，redis将会从缓存中删除掉该key. 即使是有过期时间的key，redis也会在持久化时将其写到硬盘中，并把相对过期时间改为绝对的Unix过期时间。 在一个有设置过期时间的key上重复设置过期时间将会覆盖原先设置的过期时间.
     * 
     * @param key
     * @param seconds
     * @return 返回1表示成功设置过期时间，返回0表示key不存在.
     */
    public Long expire(byte[] key, int seconds) {
        return null;
    };

    /**
     * 为key设置一个特定的过期时间，单位为秒。过期时间一到，redis将会从缓存中删除掉该key. 即使是有过期时间的key，redis也会在持久化时将其写到硬盘中，并把相对过期时间改为绝对的Unix过期时间。 在一个有设置过期时间的key上重复设置过期时间将会覆盖原先设置的过期时间。
     * 
     * @param key
     * @param unixTime
     * @return 返回1表示成功设置过期时间，返回0表示key不存在.
     */
    public Long expire(String key, int seconds) {
        return null;
    };

    /**
     * 与{@link expire}不一样，expireAt设置的时间不是能存活多久，而是固定的UNIX时间（从1970年开始算起），单位为秒tag.
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(byte[] key, long unixTime) {
        return null;
    };

    /**
     * 与{@link expire}不一样，expireAt设置的时间不是能存活多久，而是固定的UNIX时间（从1970年开始算起），单位为秒tag.
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(String key, long unixTime) {
        return null;
    };

    /**
     * 根据key获取对象.
     * 
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        return null;
    };

    /**
     * 获取单个值
     * 
     * @param key
     * @return value
     */
    public String get(String key) {
        return null;
    };

    /**
     * 获取所有分片信息.
     * 
     * @return
     */
    public Collection<JedisShardInfo> getAllShardInfo() {
        return null;
    };

    /**
     * 获取所有节点.
     * 
     * @return
     */
    public Collection<Jedis> getAllShards() {
        return null;
    };

    /**
     * 根据key和偏移获取值.
     * 
     * @param key
     * @param offset
     * @return
     */
    public Boolean getbit(String key, long offset) {
        return null;
    };

    /**
     * 根据key获取tag.
     * 
     * @param key
     * @return
     */
    public String getKeyTag(String key) {
        return null;
    };

    /**
     * 根据key和开始和结束偏移获取value.
     * 
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    public String getrange(String key, long startOffset, long endOffset) {
        return null;
    };

    /**
     * 添加.
     * 
     * @param key
     * @param value
     * @return
     */
    public byte[] getSet(byte[] key, byte[] value) {
        return null;
    };

    /**
     * 添加.
     * 
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        return null;
    };

    /**
     * 获取分片.
     * 
     * @param key
     * @return
     */
    public Jedis getShard(byte[] key) {
        return null;
    };

    /**
     * 获取分片.
     * 
     * @param key
     * @return
     */
    public Jedis getShard(String key) {
        return null;
    };

    /**
     * 获取分片.
     * 
     * @param key
     * @return
     */
    public JedisShardInfo getShardInfo(byte[] key) {
        return null;
    };

    /**
     * 获取分片.
     * 
     * @param key
     * @return
     */
    public JedisShardInfo getShardInfo(String key) {
        return null;
    };

    /**
     * 删除名称为key的hash中键为field的域.
     * 
     * @param key
     * @param field
     * @return
     */
    public Long hdel(byte[] key, byte[] field) {
        return null;
    };

    /**
     * 删除名称为key的hash中键为field的域.
     * 
     * @param key
     * @param field
     * @return
     */
    public Long hdel(String key, String field) {
        return null;
    };

    /**
     * 名称为key的hash中是否存在键为field的域.
     * 
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(byte[] key, byte[] field) {
        return null;
    };

    /**
     * 名称为key的hash中是否存在键为field的域.
     * 
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        return null;
    };

    /**
     * 返回名称为key的hash中field对应的value.
     * 
     * @param key
     * @param field
     * @return
     */
    public byte[] hget(byte[] key, byte[] field) {
        return null;
    };

    /**
     * 返回名称为key的hash中field对应的value.
     * 
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return null;
    };

    /**
     * 返回名称为key的hash中所有的键（field）及其对应的value.
     * 
     * @param key
     * @return
     */
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        return null;
    };

    /**
     * 返回名称为key的hash中所有的键（field）及其对应的value.
     * 
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        return null;
    };

    /**
     * 将名称为key的hash中field的value增加integer.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrBy(byte[] key, byte[] field, long value) {
        return null;
    };

    /**
     * 将名称为key的hash中field的value增加integer.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrBy(String key, String field, long value) {
        return null;
    };

    /**
     * 返回名称为key的hash中所有键.
     * 
     * @param key
     * @return
     */
    public Set<byte[]> hkeys(byte[] key) {
        return null;
    };

    /**
     * 返回名称为key的hash中所有键.
     * 
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        return null;
    };

    /**
     * 返回名称为key的hash中元素个数tag.
     * 
     * @param key
     * @return
     */
    public Long hlen(byte[] key) {
        return null;
    };

    /**
     * 返回名称为key的hash中元素个数tag.
     * 
     * @param key
     * @return
     */
    public Long hlen(String key) {
        return null;
    };

    /**
     * 返回名称为key的hash中field i对应的value.
     * 
     * @param key
     * @param fields
     * @return
     */
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        return null;
    };

    /**
     * 返回名称为key的hash中field i对应的value.
     * 
     * @param key
     * @param fields
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        return null;
    };

    /**
     * 向名称为key的hash中添加元素tag.
     * 
     * @param key
     * @param hash
     * @return
     */
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return null;
    };

    /**
     * 向名称为key的hash中添加元素tag.
     * 
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        return null;
    };

    /**
     * 向名称为key的hash中添加元素tag.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(byte[] key, byte[] field, byte[] value) {
        return null;
    };

    /**
     * 向名称为key的hash中添加元素tag.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(String key, String field, String value) {
        return null;
    };

    /**
     * 只有当参数中的Key或Field不存在的情况下，为指定的Key设定Field/Value对，否则该命令不会进行任何操作tag.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return null;
    };

    /**
     * 只有当参数中的Key或Field不存在的情况下，为指定的Key设定Field/Value对，否则该命令不会进行任何操作tag.
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        return null;
    };

    /**
     * 返回指定key的所有value值tag.
     * 
     * @param key
     * @return
     */
    public Collection<byte[]> hvals(byte[] key) {
        return null;
    };

    /**
     * 返回指定key的所有value值tag.
     * 
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        return null;
    };

    /**
     * 和decr相反,第二个参数是制定长度 如果是byte[] 就是把key的字符串转成byte[]数组形式.
     * 
     * @param key
     * @return
     */
    public Long incr(byte[] key) {
        return null;
    };

    /**
     * 和decr相反,第二个参数是制定长度 如果是byte[] 就是把key的字符串转成byte[]数组形式.
     * 
     * @param key
     * @return
     */
    public Long incr(String key) {
        return null;
    };

    /**
     * 指定增加的长度tag.
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(byte[] key, long integer) {
        return null;
    };

    /**
     * 指定增加的长度tag.
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, long integer) {
        return null;
    };

    /**
     * 根据下标返回相应的value.
     * 
     * @param key
     * @param index
     * @return
     */
    public byte[] lindex(byte[] key, int index) {
        return null;
    };

    /**
     * 根据下标返回相应的value.
     * 
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, long index) {
        return null;
    };

    /**
     * 在指定的元素前面或是后面插入一个元素.
     * 
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return
     */
    public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        return null;
    };

    /**
     * 在指定的元素前面或是后面插入一个元素.
     * 
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return
     */
    public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
        return null;
    };

    /**
     * key存在是返回链表中元素的数量,如果可以不存在则返回0,如果key值不是链表则返回相应的错误信息tag.
     * 
     * @param key
     * @return
     */
    public Long llen(byte[] key) {
        return null;
    };

    /**
     * key存在是返回链表中元素的数量,如果可以不存在则返回0,如果key值不是链表则返回相应的错误信息tag.
     * 
     * @param key
     * @return
     */
    public Long llen(String key) {
        return null;
    };

    /**
     * 根据key取出链表中第一个值tag.
     * 
     * @param key
     * @return
     */
    public byte[] lpop(byte[] key) {
        return null;
    };

    /**
     * 根据key取出链表中第一个值tag.
     * 
     * @param key
     * @return
     */
    public String lpop(String key) {
        return null;
    };

    /**
     * 以链表的形式往里添加先进后出.
     * 
     * @param key
     * @param string
     * @return
     */
    public Long lpush(byte[] key, byte[] string) {
        return null;
    };

    /**
     * 以链表的形式往里添加先进后出.
     * 
     * @param key
     * @param string
     * @return
     */
    public Long lpush(String key, String string) {
        return null;
    };

    /**
     * 根据key值取出list当中从第几个到第几个的值.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<byte[]> lrange(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 根据key值取出list当中从第几个到第几个的值.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        return null;
    };

    /**
     * 删除制定数量的value值,从前往后删除,删除成功返回1,失败返回0.
     * 
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(byte[] key, int count, byte[] value) {
        return null;
    };

    /**
     * 删除制定数量的value值,从前往后删除,删除成功返回1,失败返回0.
     * 
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(String key, long count, String value) {
        return null;
    };

    /**
     * 根据下标替换值,如果下标不存在或是key不存在会报相应的异常.
     * 
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(byte[] key, int index, byte[] value) {
        return null;
    };

    /**
     * 根据下标替换值,如果下标不存在或是key不存在会报相应的异常.
     * 
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(String key, long index, String value) {
        return null;
    };

    /**
     * 留指定下标的数据,只留下下标为10-11的元素其他全部删除,如果长度小于10则全部删除tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 留指定下标的数据,只留下下标为10-11的元素其他全部删除,如果长度小于10则全部删除tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(String key, long start, long end) {
        return null;
    };

    /**
     * 通过管道，已弃用.
     * 
     * @param shardedJedisPipeline
     * @return
     */
    public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
        return null;
    };

    /**
     * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素，.如果该Key不存，返回null.
     * 
     * @param key
     * @return
     */
    public byte[] rpop(byte[] key) {
        return null;
    };

    /**
     * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素，.如果该Key不存，返回null.
     * 
     * @param key
     * @return
     */
    public String rpop(String key) {
        return null;
    };

    /**
     * 根据链表规则，从尾部插入数据.
     * 
     * @param key
     * @param string
     * @return
     */
    public Long rpush(byte[] key, byte[] string) {
        return null;
    };

    /**
     * 根据链表规则，从尾部插入数据.
     * 
     * @param key
     * @param string
     * @return
     */
    public Long rpush(String key, String string) {
        return null;
    };

    /**
     * 添加,如果执行该命令之前，该Key并不存在，该命令将会创建一个新的Set， 此后再将参数中的成员陆续插入.如果该Key的Value不是Set类型，该命令将返回相关的错误信息.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long sadd(byte[] key, byte[]... member) {
        return null;
    };

    /**
     * * 添加,如果执行该命令之前，该Key并不存在，该命令将会创建一个新的Set， 此后再将参数中的成员陆续插入.如果该Key的Value不是Set类型，该命令将返回相关的错误信息.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long sadd(String key, String... member) {
        return null;
    };

    /**
     * 获取Set中成员的数量.
     * 
     * @param key
     * @return
     */
    public Long scard(byte[] key) {
        return null;
    };

    /**
     * 获取Set中成员的数量.
     * 
     * @param key
     * @return
     */
    public Long scard(String key) {
        return null;
    };

    /**
     * 给数据库中名称为key的对象赋予值value.
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(byte[] key, byte[] value) {
        return null;
    };

    /**
     * 给数据库中名称为key的对象赋予值value.
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        return null;
    };

    /**
     * 设置在指定Offset上BIT的值，该值只能为1或0，在设定后该命令返回该Offset上原有的BIT值.如果指定Key不存在，该命令将创建一个新值， 并在指定的Offset上设定参数中的BIT值.
     * 如果Offset大于Value的字符长度，Redis将拉长Value值并在指定Offset上设置参数中的BIT值，中间添加的BIT值为0.最后需要说明的是Offset值必须大于0.
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Boolean setbit(String key, long offset, boolean value) {
        return null;
    };

    /**
     * 设置一个字符串在服务器的存活时间,第二个参数为秒数.
     * 
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public String setex(byte[] key, int seconds, byte[] value) {
        return null;
    };

    /**
     * 设置一个字符串在服务器的存活时间,第二个参数为秒数.
     * 
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public String setex(String key, int seconds, String value) {
        return null;
    };

    /**
     * 如果key不存在则保存key所对应的value,如果key存在则不做任何操作tag.
     * 
     * @param key
     * @param value
     * @return
     */
    public Long setnx(byte[] key, byte[] value) {
        return null;
    };

    /**
     * 如果key不存在则保存key所对应的value,如果key存在则不做任何操作tag.
     * 
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        return null;
    };

    /**
     * 从第offset个开始替换tag.
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Long setrange(String key, long offset, String value) {
        return null;
    };

    /**
     * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中tag.
     * 
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(byte[] key, byte[] member) {
        return null;
    };

    /**
     * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中tag.
     * 
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        return null;
    };

    /**
     * 获取set集合中所有的成员,如果key不存在会报空.
     * 
     * @param key
     * @return
     */
    public Set<byte[]> smembers(byte[] key) {
        return null;
    };

    /**
     * 获取set集合中所有的成员,如果key不存在会报空.
     * 
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        return null;
    };

    /**
     * 根据key获取对象然后排序.
     * 
     * @param key
     * @return
     */
    public List<byte[]> sort(byte[] key) {
        return null;
    };

    /**
     * 根据key获取对象然后排序.
     * 
     * @param key
     * @param sortingParameters
     * @return
     */
    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        return null;
    };

    /**
     * 根据key获取对象然后排序.
     * 
     * @param key
     * @return
     */
    public List<String> sort(String key) {
        return null;
    };

    /**
     * 根据key获取对象然后排序.
     * 
     * @param key
     * @param sortingParameters
     * @return
     */
    public List<String> sort(String key, SortingParams sortingParameters) {
        return null;
    };

    /**
     * 随机返回并删除名称为key的set中一个元素tag.
     * 
     * @param key
     * @return
     */
    public byte[] spop(byte[] key) {
        return null;
    };

    /**
     * 随机返回并删除名称为key的set中一个元素tag.
     * 
     * @param key
     * @return
     */
    public String spop(String key) {
        return null;
    };

    /**
     * 在set集合中随机取一个值,但是不会删除该值.
     * 
     * @param key
     * @return
     */
    public byte[] srandmember(byte[] key) {
        return null;
    };

    /**
     * 在set集合中随机取一个值,但是不会删除该值.
     * 
     * @param key
     * @return
     */
    public String srandmember(String key) {
        return null;
    };

    /**
     * 删除set集合中指定的成员,值存在返回1不存在返回0.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long srem(byte[] key, byte[] member) {
        return null;
    };

    /**
     * 删除set集合中指定的成员,值存在返回1不存在返回0.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long srem(String key, String member) {
        return null;
    };

    /**
     * 返回名称为key的string的value的子串tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public byte[] substr(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 返回名称为key的string的value的子串tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String substr(String key, int start, int end) {
        return null;
    };

    /**
     * 获得一个key的活动时间tag.
     * 
     * @param key
     * @return
     */
    public Long ttl(byte[] key) {
        return null;
    };

    /**
     * 获得一个key的活动时间tag.
     * 
     * @param key
     * @return
     */
    public Long ttl(String key) {
        return null;
    };

    /**
     * 返回值的类型.
     * 
     * @param key
     * @return
     */
    public String type(byte[] key) {
        return null;
    };

    /**
     * 返回值的类型.
     * 
     * @param key
     * @return
     */
    public String type(String key) {
        return null;
    };

    /**
     * 向名称为key的zset中添加元素member，score用于排序.如果该元素已经存在，则根据score更新该元素的顺序.
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(byte[] key, double score, byte[] member) {
        return null;
    };

    /**
     * 向名称为key的zset中添加元素member，score用于排序.如果该元素已经存在，则根据score更新该元素的顺序.
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
        return null;
    };

    /**
     * 向名称为key的zset中添加多个元素member.
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return null;
    };

    /**
     * 向名称为key的zset中添加多个元素member.
     * 
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return null;
    };

    /**
     * 返回名称为key的zset的基数tag.
     * 
     * @param key
     * @return
     */
    public Long zcard(byte[] key) {
        return null;
    };

    /**
     * 返回名称为key的zset的基数tag.
     * 
     * @param key
     * @return
     */
    public Long zcard(String key) {
        return null;
    };

    /**
     * 根据区间来统计数量(min <= score <= max).
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(byte[] key, double min, double max) {
        return null;
    };

    /**
     * 根据区间来统计数量(min <= score <= max).
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, double min, double max) {
        return null;
    };

    /**
     * 给指定的成员加分数,成员存在则累加,不存在则创建.
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(byte[] key, double score, byte[] member) {
        return null;
    };

    /**
     * 给指定的成员加分数,成员存在则累加,不存在则创建.
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        return null;
    };

    /**
     * 该命令返回顺序在参数start和stop指定范围内的成员，这里start和stop参数都是0-based，即0表示第一个成员，-1表示最后一个成员. <br>
     * 如果start大于该Sorted-Set中的最大索引值，或start > stop，此时一个空集合将被返回.如果stop大于最大索引值，该命令将返回从start到集合的最后一个成员.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<byte[]> zrange(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 该命令返回顺序在参数start和stop指定范围内的成员，这里start和stop参数都是0-based，即0表示第一个成员，-1表示最后一个成员. <br>
     * 如果start大于该Sorted-Set中的最大索引值，或start >stop，此时一个空集合将被返回.如果stop大于最大索引值，该命令将返回从start到集合的最后一个成员.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, int start, int end) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 可选参数LIMIT用于限制返回成员的数量范围.可选参数offset表示从符合条件的第offset个成员开始返回，同时返回count个成员.
     * 
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 该命令将返回分数在min和max之间的所有成员，即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(String key, int start, int end) {
        return null;
    };

    /**
     * Sorted-Set中的成员都是按照分数从低到高的顺序存储，该命令将返回参数中指定成员的位置值，其中0表示第一个成员，它是Sorted-Set中分数最低的成员.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrank(byte[] key, byte[] member) {
        return null;
    };

    /**
     * Sorted-Set中的成员都是按照分数从低到高的顺序存储，该命令将返回参数中指定成员的位置值，其中0表示第一个成员，它是Sorted-Set中分数最低的成员.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member) {
        return null;
    };

    /**
     * 删除成员,返回删除成员的数量tag.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrem(byte[] key, byte[] member) {
        return null;
    };

    /**
     * 删除成员,返回删除成员的数量tag.
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrem(String key, String member) {
        return null;
    };

    /**
     * 删除名称为key的zset中rank >= min且rank <= max的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 删除名称为key的zset中rank >= min且rank <= max的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key, int start, int end) {
        return null;
    };

    /**
     * 删除名称为key的zset中score >= min且score <= max的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(byte[] key, double start, double end) {
        return null;
    };

    /**
     * 删除名称为key的zset中score >= min且score <= max的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key, double start, double end) {
        return null;
    };

    /**
     * 返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<byte[]> zrevrange(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素tag.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, int start, int end) {
        return null;
    };

    /**
     * 机制与zrangeByScore一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return null;
    };

    /**
     * 机制与zrangeByScore一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return null;
    };

    /**
     * 机制与zrangeByScore一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return null;
    };

    /**
     * 机制与zrangeByScore一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return null;
    };

    /**
     * 机制与zrangeByScoreWithScores一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return null;
    };

    /**
     * 机制与zrangeByScoreWithScores一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return null;
    };

    /**
     * 机制与zrangeByScoreWithScores一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return null;
    };

    /**
     * 机制与zrangeByScoreWithScores一样，只是返回结果为降序排序.
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return null;
    };

    /**
     * 返回有序集key中，指定区间内的成员。其中成员的位置按score值递减(从大到小)来排列。具有相同score值的成员按字典序的反序排列. 除了成员按score值递减的次序排列这一点外，ZREVRANGE命令的其他方面和ZRANGE命令一样.
     * 
     * @param key
     * @param start
     * @param end
     * @return 指定范围的元素列表(可选是否含有分数).
     */
    public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
        return null;
    };

    /**
     * 返回有序集key中，指定区间内的成员。其中成员的位置按score值递减(从大到小)来排列。具有相同score值的成员按字典序的反序排列. 除了成员按score值递减的次序排列这一点外，ZREVRANGE命令的其他方面和ZRANGE命令一样.
     * 
     * @param key
     * @param start
     * @param end
     * @return 指定范围的元素列表(可选是否含有分数).
     */
    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        return null;
    };

    /**
     * 返回有序集key中成员member的排名，其中有序集成员按score值从大到小排列。排名以0为底，也就是说，score值最大的成员排名为0。 使用ZRANK命令可以获得成员按score值递增(从小到大)排列的排名。
     * 
     * @param key
     * @param member
     * @return 如果member是有序集key的成员，返回member的排名。整型数字。 如果member不是有序集key的成员，返回Bulk reply: nil.
     */
    public Long zrevrank(byte[] key, byte[] member) {
        return null;
    };

    /**
     * 返回有序集key中成员member的排名，其中有序集成员按score值从大到小排列。排名以0为底，也就是说，score值最大的成员排名为0。 使用ZRANK命令可以获得成员按score值递增(从小到大)排列的排名。
     * 
     * @param key
     * @param member
     * @return 如果member是有序集key的成员，返回member的排名。整型数字。 如果member不是有序集key的成员，返回Bulk reply: nil.
     */
    public Long zrevrank(String key, String member) {
        return null;
    };

    /**
     * 返回有序集key中，成员member的score值。 如果member元素不是有序集key的成员，或key不存在，返回nil.
     * 
     * @param key
     * @param member
     * @return member成员的score值（double型浮点数）
     */
    public Double zscore(byte[] key, byte[] member) {
        return null;
    };

    /**
     * 返回有序集key中，成员member的score值。 如果member元素不是有序集key的成员，或key不存在，返回nil.
     * 
     * @param key
     * @param member
     * @return member成员的score值（double型浮点数）
     */
    public Double zscore(String key, String member) {
        return null;
    };

    /**
     * 获取所有节点.
     * 
     * @return
     */
    public Map<String, JedisPool> getClusterNodes() {
        return null;
    }

}
