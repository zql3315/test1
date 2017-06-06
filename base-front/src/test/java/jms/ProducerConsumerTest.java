package jms;

import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath*:/spring/jms-spring.xml" })
public class ProducerConsumerTest {

//    @Autowired
//    private ProducerService producerService;
//
//    @Autowired
//    @Qualifier("queueDestination")
//    private Destination destination;
//
//    @Autowired
//    @Qualifier("sessionAwareQueue")
//    private Destination sessionAwareQueue;
//
//    @Autowired
//    @Qualifier("adapterQueue")
//    private Destination adapterQueue;

    @Test
    public void testSend() {
        // for (int i=0; i<2; i++) {
        // producerService.sendMessage(destination, "你好，生产者！这是消息：" + (i+1));
        // }
//        producerService.sendMessage(destination, "你好，生产者！这是消息：");
        // Email email = new Email("111", "222", "333");
        // producerService.sendMessage(destination, email);
    }

    @Test
    public void testSessionAwareMessageListener() {
//        producerService.sendMessage(sessionAwareQueue, "测试SessionAwareMessageListener");
    }

    @Test
    public void testMessageListenerAdapter() {
//        producerService.sendMessage(adapterQueue, "测试MessageListenerAdapter");
    }

}
