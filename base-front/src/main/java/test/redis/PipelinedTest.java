package test.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * redis的管道就是可以在给redis服务端发送了一个命令后，不用等待该命令执行返回，而继续发送下一个命令。最终将结果一起返回给客户端，这样可以显著提供响应时间
 * 
 * 执行的结果是： 8368/243
 * 很显然，可以发现，使用管道比不适用的效率提高了很多
 * @author n004881
 *
 */
public class PipelinedTest {

    public static void main(String[] args) {
        Jedis jedis = JedisTestUtil.getInstance().getJedis();
        //不使用管道的测试  
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jedis.lpush("key", "" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        jedis.flushDB();
        //使用管道的测试  
        long start2 = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 100000; i++) {
            pipeline.lpush("key", "" + i);
        }
        List<Object> list = pipeline.syncAndReturnAll();
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
//        jedis.flushAll();

        JedisTestUtil.getInstance().returnJedis(jedis);
    }

}
