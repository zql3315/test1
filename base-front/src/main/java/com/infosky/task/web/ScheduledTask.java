package com.infosky.task.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author n004881
 * @version [版本号, 2015年3月27日]
 * 
 */
@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    public void scheduledExport() {
        logger.info("定时导出任务触发");
    }

}
