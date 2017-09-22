package com.infosky.shiro.session.scheduler;

import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重写session验证调度器
 * 
 * 其直接调用SessionDAO的getActiveSessions方法获取所有会话进行验证，
 * 
 * 如果会话比较多，会影响性能；可以考虑如分页获取会话并进行验证、关闭验证，或者其他自定义实现的优化
 * 
 * @author n004881
 */
public class MyExecutorServiceSessionValidationScheduler extends ExecutorServiceSessionValidationScheduler {

    private static final Logger log = LoggerFactory.getLogger(MyExecutorServiceSessionValidationScheduler.class);

    /**
     * //TODO 添加override说明
     * @see org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler#enableSessionValidation()
     **/
    public void enableSessionValidation() {
        log.debug("======MySessionValidationScheduler:enableSessionValidation==========");
        super.enableSessionValidation();
    }

    /**
     * 守护线程定时执行清理用户直接关闭浏览器造成的孤立会话.
     * @see org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler#run()
     **/
    public void run() {
        log.debug("======MySessionValidationScheduler:run==========");
        super.run();
    }
}
