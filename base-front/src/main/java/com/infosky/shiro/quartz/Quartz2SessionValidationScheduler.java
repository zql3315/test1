package com.infosky.shiro.quartz;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author n004881
 * 
 * 默认的shiro-quartz1.3.1以下中的实现是针对quartz1.6版本的实现（详细源码请查看QuartzSessionValidationScheduler），
 * 在quartz2.2以上版本中，SimppleTrigger为接口，所以无法实例化。所以需要重新实现：
 *
 */
public class Quartz2SessionValidationScheduler extends QuartzSessionValidationScheduler {

    public static final long DEFAULT_SESSION_VALIDATION_INTERVAL = 3600000L;

    private static final String JOB_NAME = "SessionValidationJob";

    private static final Logger log = LoggerFactory.getLogger(Quartz2SessionValidationScheduler.class);

    private boolean schedulerImplicitlyCreated = false;

    private boolean enabled = false;

    private ValidatingSessionManager sessionManager;

    private long sessionValidationInterval = 3600000L;

    public Quartz2SessionValidationScheduler() {
    }

    public Quartz2SessionValidationScheduler(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void enableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Scheduling session validation job using Quartz with session validation interval of [" + this.sessionValidationInterval + "]ms...");
        }

        try {
            SimpleTrigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity(JOB_NAME, "DEFAULT")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(sessionValidationInterval)).build();

            JobDetail detail = JobBuilder.newJob(QuartzSessionValidationJob.class).withIdentity(JOB_NAME, "DEFAULT").build();
            detail.getJobDataMap().put("sessionManager", this.sessionManager);
            Scheduler scheduler = getScheduler();

            scheduler.scheduleJob(detail, trigger);
            if (this.schedulerImplicitlyCreated) {
                scheduler.start();
                if (log.isDebugEnabled()) {
                    log.debug("Successfully started implicitly created Quartz Scheduler instance.");
                }
            }
            this.enabled = true;

            if (log.isDebugEnabled()) log.debug("Session validation job successfully scheduled with Quartz.");
        } catch (SchedulerException e) {
            if (log.isErrorEnabled()) log.error("Error starting the Quartz session validation job.  Session validation may not occur.", e);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
