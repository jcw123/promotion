package com.im.sky;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author jiangchangwei
 * @since 2024/11/11
 */
public class QuartzTest {

    @Test
    public void testBase() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 定义 JobDetail
        JobDetail job = JobBuilder.newJob(BaseJob.class)
                .withIdentity("myJob", "group1")
                .build();
        // 创建 Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        Thread.sleep(1000 * 20);
        String s = "\n";
    }

    public static class BaseJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("test job");
        }
    }
}
