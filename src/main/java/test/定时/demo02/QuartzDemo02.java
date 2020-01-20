package test.定时.demo02;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述  Quartz 定时器实现
 */
public class QuartzDemo02 {
    public static void main(String[] args) throws InterruptedException, SchedulerException {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        // 开始
        scheduler.start();
        // job 唯一标识 test.test-1
        JobKey jobKey = new JobKey("test" , "test-1");
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(jobKey).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("test" , "test")
                // 延迟一秒执行
                .startAt(new Date(System.currentTimeMillis() + 1000))
                // 每隔一秒执行 并一直重复
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail , trigger);

        Thread.sleep(5000);
        // 删除job
        scheduler.deleteJob(jobKey);
    }

}
