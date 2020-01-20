package test.定时任务.demo01;

import cn.hutool.core.lang.Assert;
import org.quartz.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述
 */
public class QuartzManager {
    private Scheduler clusterScheduler;//项目启动时注入

    public void init() throws SchedulerException, ParseException {
        JobKey jobKey = JobKey.jobKey("customJob_name", "customJob_group");
        //clusterScheduler = new StdSchedulerFactory().getScheduler();
        JobDetail jobDetail = clusterScheduler.getJobDetail(jobKey);//xml中配置了
        TriggerKey triggerKey = TriggerKey.triggerKey("customTrigger_name", "customTrigger_group");
        boolean isExists = clusterScheduler.checkExists(triggerKey);
        if(isExists){
            clusterScheduler.unscheduleJob(triggerKey);//停止调度当前Job任务
        }
        String cron = (10 % 50) + " 0/1 * ? * *" ;//Cron表达式:每隔一分钟执行一次
        Assert.isTrue(CronExpression.isValidExpression(cron), "invalid cron = " + cron);
        Date startDate = new Date(System.currentTimeMillis()+ 1*60*1000);//开始执行时间
        Map<String, Object> dataMap = new HashMap<String, Object>();//参数
        dataMap.put("params", "taiyang");

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .startAt(startDate)
                .build();
        trigger.getJobDataMap().putAll(dataMap);//传参
        clusterScheduler.scheduleJob(trigger);
    }

    public void setClusterScheduler(Scheduler clusterScheduler) {
        this.clusterScheduler = clusterScheduler;
    }

}
