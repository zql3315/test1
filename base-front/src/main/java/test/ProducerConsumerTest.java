package test;

import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.infosky.jms.entity.Email;
import com.infosky.jms.service.ProducerService;

/**
 * jms测试
 * 
 * @author n004881
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath*:/jms/jms-spring.xml"
})
public class ProducerConsumerTest {

    @Autowired
    private ProducerService producerService;

    @Autowired
    @Qualifier("queueDestination")
    private Destination queueDestination;

    @Autowired
    @Qualifier("sessionAwareQueue")
    private Destination sessionAwareQueue;

    @Autowired
    @Qualifier("adapterQueue")
    private Destination adapterQueue;

    @Test
    public void testSend() {
        // for (int i = 0; i < 2; i++) {
        // producerService.sendMessage(queueDestination, "你好，生产者！这是消息：" + (i + 1));
        // }
        producerService.sendMessage(queueDestination, "你好，生产者！这是消息：123123123");
    }

    @Test
    public void testSendObject() {
        Email email = new Email("111", "222", "333");
        producerService.sendMessage(queueDestination, email);
    }

    @Test
    public void testSessionAwareMessageListener() {
        producerService.sendMessage(sessionAwareQueue, "测试SessionAwareMessageListener");
    }

    @Test
    public void testMessageListenerAdapter() {
        producerService.sendMessage(adapterQueue, "测试MessageListenerAdapter");
    }

}
