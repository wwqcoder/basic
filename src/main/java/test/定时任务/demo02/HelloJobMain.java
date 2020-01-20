package test.定时任务.demo02;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述
 */
public class HelloJobMain {
    public static void main(String[] args) {
        try {
            //1:调度器Schedule
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


            //2:任务实例 JobDeatil hellojob任务名称 group1任务分组名称
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).
                    withIdentity("hellojob","group1").
                    usingJobData("jobDetail传递我们想要的参数到JobExecutionContext中去","嘿嘿嘿").
                    build();

            System.out.println("job的任务的名称:"+jobDetail.getKey().getName());
            System.out.println("job的任务组的名称:"+jobDetail.getKey().getGroup());
            System.out.println("job的任务class:"+jobDetail.getJobClass().getName());

            //3:触发器Trigger' trigger1触发器名称  group1触发器分组名 每5S执行一次打印 startNow马上启动触发器
            Trigger trigger = TriggerBuilder.newTrigger().
                    withIdentity("trigger1","group1").startNow().
                    withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).
                    usingJobData("trigger传递我们想要的参数到JobExecutionContext中去","哈哈哈").
                    build();

            //让调度器 使触发器和任务关联
            scheduler.scheduleJob(jobDetail,trigger);

            //启动任务调度器
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
