package test.定时任务.demo02;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述
 */
public class HelloJob implements Job {
    public HelloJob(){
        System.out.println("quartz 每次任务执行 都会创建一个新的HelloJob 对象实例");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("************************************进入任务************************************************");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
        System.out.println(formatter.format(now));

        //通过JobExecutionContext对象 获取JobDetail
        System.out.println("通过JobExecutionContext对象,获取job的任务的名称:"+jobExecutionContext.getJobDetail().getKey().getName());
        System.out.println("通过JobExecutionContext对象,获取job的任务组的名称:"+jobExecutionContext.getJobDetail().getKey().getGroup());
        System.out.println("通过JobExecutionContext对象,获取job的任务class(带路径):"+jobExecutionContext.getJobDetail().getJobClass().getName());
        System.out.println("通过JobExecutionContext对象,获取job的任务class(不带路径):"+jobExecutionContext.getJobDetail().getJobClass().getSimpleName());

        System.out.println("------------------------------------------------------------");

        //从JobDetail中获取JobExecutionContext中的usingJobData的内容
        JobDataMap jobDataMap_JobDetail = jobExecutionContext.getJobDetail().getJobDataMap();
        jobDataMap_JobDetail.forEach((k,v)->{
            System.err.println("使用java8循环JobDetail中 jobDataMap key : " +k + " value : " + v);
        });

        System.out.println("------------------------------------------------------------");

        //通过JobExecutionContext对象 获取Trigger
        System.out.println("通过JobExecutionContext对象,获取trigger的任务的名称:"+jobExecutionContext.getTrigger().getKey().getName());
        System.out.println("通过JobExecutionContext对象,获取trigger的任务组的名称:"+jobExecutionContext.getTrigger().getKey().getGroup());

        System.out.println("------------------------------------------------------------");


        //从Trigger中获取JobExecutionContext中的usingJobData的内容
        JobDataMap jobDataMap_Trigger = jobExecutionContext.getTrigger().getJobDataMap();
        jobDataMap_Trigger.forEach((k,v)->{
            System.err.println("使用java8循环Trigger中 jobDataMap key : " +k + " value : " + v);
        });

        System.out.println("------------------------------------------------------------");

        Instant instant1 = jobExecutionContext.getFireTime().toInstant();
        ZoneId zoneId1 = ZoneId.systemDefault();
        LocalDateTime taskTimeNow = instant1.atZone(zoneId1).toLocalDateTime();
        System.out.println("获取当前任务执行的时间:"+formatter.format(taskTimeNow));

        Instant instant2 = jobExecutionContext.getNextFireTime().toInstant();
        ZoneId zoneId2 = ZoneId.systemDefault();
        LocalDateTime taskTimeNext = instant2.atZone(zoneId2).toLocalDateTime();
        System.out.println("获取任务下次执行的时间:"+formatter.format(taskTimeNext));
    }

}
