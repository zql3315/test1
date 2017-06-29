package test.bf;

import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.Jedis;
import test.redis.JedisTestUtil;

/**
 * 并发模拟
 * 场景： 同一个账号，一次性发出多个请求,部分用户通过浏览器的插件或者其他工具，在秒杀开始的时间里，以自己的账号，一次发送上百甚至更多的请求。实际上，这样的用户破坏了秒杀和抢购的公平性。
 *     这种请求在某些没有做数据安全处理的系统里，也可能造成另外一种破坏，导致某些判断条件被绕过。例如一个简单的领取逻辑，先判断用户是否有参与记录，如果没有则领取成功，最后写入到参与记录中。
 *     这是个非常简单的逻辑，但是，在高并发的场景下，存在深深的漏洞。多个并发请求通过负载均衡服务器，分配到内网的多台Web服务器，它们首先向存储发送查询请求，
 *     然后，在某个请求成功写入参与记录的时间差内，其他的请求获查询到的结果都是“没有参与记录”。这里，就存在逻辑判断被绕过的风险。
 * 应对方案：在程序入口处，一个账号只允许接受1个请求，其他请求过滤。不仅解决了同一个账号，发送N个请求的问题，还保证了后续的逻辑流程的安全。
 *     实现方案，可以通过Redis这种内存缓存服务，写入一个标志位（只允许1个请求写成功，结合watch的乐观锁的特性），成功写入的则可以继续参加。
 * @author n004881
 *
 */
public class Concurrency1 {

    public static long flag = 1;

    static int threadCount = 3;

    public static void main(String[] args) {
        //创建2个点的CountDownLatch对象  
        //CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        System.out.println("===============传统逻辑===========");
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {

                public void run() {
                    test1("张三");
                    //执行子任务完毕之后，countDown减少一个点  
                    countDownLatch.countDown();
                }
            }, "线程一").start();
        }
        //阻塞Main线程，执行子线程workThread1和workThread2，完毕后继续执行后续的逻辑  
        //        try {
        //            workThread1.join();
        //            workThread2.join();
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        try {
            //调用await方法阻塞当前线程，等待子线程完成后在继续执行  
            //当我们调用countDown方法时，N就会减1，await方法会阻塞当前线程，直到N变成0。
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============优化逻辑===========");
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {

                public void run() {
                    test2("张三");
                }
            }, "线程二").start();
        }
    }

    public static void test1(String name) {
        if (check1(name)) {
            System.out.println(Thread.currentThread().getName() + "===" + name + "抢到票了");
        }
    }

    public static void test2(String name) {
        if (check2(name)) {
            System.out.println(Thread.currentThread().getName() + "===" + name + "抢到票了");
        }
    }

    /**
     * 传统逻辑
     * 根据传统数据库里面的值来判断是否有参与记录
     * @throws InterruptedException 
     */
    public static boolean check1(String name) {
        long _flag = getFlag(name);
        if (_flag == 1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setFlag(name);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 优化逻辑
     * 利用redis里面的原子性来判断是否有参与记录
     * @throws InterruptedException 
     */
    public static boolean check2(String name) {
        Jedis jedis = JedisTestUtil.getInstance().getJedis();
        Long flag = jedis.incr(name);
        JedisTestUtil.getInstance().returnJedis(jedis);
        if (flag == 1)
            return true;
        else
            return false;
    }

    /**
     * 从数据库中取出数据
     * 
     * @param name 条件
     * @return
     */
    public static long getFlag(String name) {
        //something by name
        return flag;
    }

    /**
     * 改变数据库中的数据
     * 
     * @param name 条件
     * @return
     */
    public static void setFlag(String name) {
        //something by name
        flag = 2;;
    }
}