package test.bf;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.springframework.util.StopWatch;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import test.redis.JedisTestUtil;

/**
 * 
 * 基于redis的抢红包方案
 * 下面介绍一种基于Redis的抢红包方案。
 * 把原始的红包称为大红包，拆分后的红包称为小红包。
 * 1.小红包预先生成，插到数据库里，红包对应的用户ID是null。生成算法见另一篇blog：http://blog.csdn.NET/hengyunabc/article/details/19177877
 * 2.每个大红包对应两个redis队列，一个是未消费红包队列，另一个是已消费红包队列。开始时，把未抢的小红包全放到未消费红包队列里。
 * 未消费红包队列里是json字符串，如{userId:'789', money:'300'}。
 * 3.在redis中用一个map来过滤已抢到红包的用户。
 * 4.抢红包时，先判断用户是否抢过红包，如果没有，则从未消费红包队列中取出一个小红包，再push到另一个已消费队列中，最后把用户ID放入去重的map中。
 * 5.用一个单线程批量把已消费队列里的红包取出来，再批量update红包的用户ID到数据库里。
 * 上面的流程是很清楚的，但是在第4步时，如果是用户快速点了两次，或者开了两个浏览器来抢红包，会不会有可能用户抢到了两个红包？
 * 为了解决这个问题，采用了lua脚本方式，让第4步整个过程是原子性地执行。
 * 
 * 测试结果20个线程，每秒可以抢2.5万个，足以应付绝大部分的抢红包场景。
 * 如果是真的应付不了，拆分到几个redis集群里，或者改为批量抢红包，也足够应付。
 * 总结：
 * redis的抢红包方案，虽然在极端情况下（即redis挂掉）会丢失一秒的数据，但是却是一个扩展性很强，足以应付高并发的抢红包方案。
 * 
 * @author n004881
 *
 */
public class TestEval {

    static String host = "localhost";

    static int honBaoCount = 1_0_0000;

    static int threadCount = 20;

    static String hongBaoList = "hongBaoList";

    static String hongBaoConsumedList = "hongBaoConsumedList";

    static String hongBaoConsumedMap = "hongBaoConsumedMap";

    static Random random = new Random();

    //  -- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空  
    //  -- 参数：红包队列名， 已消费的队列名，去重的Map名，用户ID  
    //  -- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money  
    static String tryGetHongBaoScript =
    //          "local bConsumed = redis.call('hexists', KEYS[3], KEYS[4]);\n"  
    //          + "print('bConsumed:' ,bConsumed);\n"  
    "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n" 
        + "return nil\n" + "else\n" 
        + "local hongBao = redis.call('rpop', KEYS[1]);\n"
        //          + "print('hongBao:', hongBao);\n"  
        + "if hongBao then\n" 
        //先取出一个小红包 
            + "local x = cjson.decode(hongBao);\n" 
            //加入用户ID信息  
            + "x['userId'] = KEYS[4];\n" 
            + "local re = cjson.encode(x);\n" 
            // 把用户ID放到去重的set里  
            + "redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);\n"
            // 把红包放到已消费队列里  
            + "redis.call('lpush', KEYS[2], re);\n" 
            + "return re;\n" 
        + "end\n" 
    + "end\n" 
    + "return nil";

    static StopWatch watch = new StopWatch();

    public static void main(String[] args) throws InterruptedException {
        generateTestData();
        testTryGetHongBao();
    }

    /**
     * 初始化红包，把红包放到队列里面去
     * @throws InterruptedException
     */
    static public void generateTestData() throws InterruptedException {
        Jedis jedis = JedisTestUtil.getInstance().getJedis();
        jedis.flushAll();
        JedisTestUtil.getInstance().returnJedis(jedis);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; ++i) {
            final int temp = i;
            Thread thread = new Thread() {

                public void run() {
                    Jedis jedis = JedisTestUtil.getInstance().getJedis();
                    int per = honBaoCount / threadCount;
                    Map object = new HashMap();
                    for (int j = temp * per; j < (temp + 1) * per; j++) {
                        object.put("id", j);
                        object.put("money", j);
                        jedis.lpush(hongBaoList, JSONObject.fromObject(object).toString());
                    }
                    JedisTestUtil.getInstance().returnJedis(jedis);
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();
    }

    static public void testTryGetHongBao() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(threadCount);
        System.err.println("start:" + System.currentTimeMillis() / 1000);
        watch.start();
        for (int i = 0; i < threadCount; ++i) {
            final int temp = i;
            Thread thread = new Thread() {

                public void run() {
                    Jedis jedis = JedisTestUtil.getInstance().getJedis();
                    String sha = jedis.scriptLoad(tryGetHongBaoScript);
                    int j = honBaoCount / threadCount * temp;
                    while (true) {
                        Object object = jedis.eval(tryGetHongBaoScript, 4, hongBaoList, hongBaoConsumedList, hongBaoConsumedMap, "" + j);
                        j++;
                        if (object != null) {
                            //                          System.out.println("get hongBao:" + object);  
                        } else {
                            //已经取完了  
                            if (jedis.llen(hongBaoList) == 0) break;
                        }
                    }
                    JedisTestUtil.getInstance().returnJedis(jedis);
                    latch.countDown();
                }
            };
            thread.start();
        }

        latch.await();
        watch.stop();

        System.err.println("time:" + watch.getTotalTimeSeconds());
        System.err.println("speed:" + honBaoCount / watch.getTotalTimeSeconds());
        System.err.println("end:" + System.currentTimeMillis() / 1000);
    }
}
