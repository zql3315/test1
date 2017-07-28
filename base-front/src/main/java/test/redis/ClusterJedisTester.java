package test.redis;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.infosky.redis.cluster.DistributedLockManger;

/**
 * redis集群自定义锁设置
 * 
 * @author n004881
 */
public class ClusterJedisTester extends TestCase {

    private Logger logger = LoggerFactory.getLogger(ClusterJedisTester.class);

    private static String[] list = new String[] {
        "classpath:spring-redis-test.xml"
    };

    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(list, true, null);

    DistributedLockManger distributedLock = (DistributedLockManger) context.getBean("distributedLock");

    @Test
    public void testLock() throws InterruptedException {
        try {
            distributedLock.test();
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
