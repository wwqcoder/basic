package test.定时.demo03.demo0304;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import test.定时.demo02.TestJob;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述  动态增加删除
 */
@Component
public class Test {
    @Autowired
    private SchedulerFactoryBean quartzScheduler;

    public void addJob() throws SchedulerException {

        Scheduler scheduler = quartzScheduler.getScheduler();
        JobKey jobKey = new JobKey("test", "test");
        if (scheduler.checkExists(jobKey)) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(jobKey).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("test", "test")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
