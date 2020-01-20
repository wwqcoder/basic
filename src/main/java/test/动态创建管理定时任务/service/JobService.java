package test.动态创建管理定时任务.service;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import test.动态创建管理定时任务.dynamicquery.service.QuartzConfigService;
import test.动态创建管理定时任务.entity.QuartzConfigModel;
import test.动态创建管理定时任务.entity.identity.QuartzConfigIdentity;


@Service
@SuppressWarnings("all")
public class JobService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String JOBCLASSNAME = "com.itstyle.quartz.job.DynamicQuartzJob";

    private final Integer VALID = 0;
    private final Integer INVALID = 1;

    @Resource
    ApplicationContext applicationContext;

    @Resource
    Scheduler scheduler;

    @Resource
    QuartzConfigService quartzConfigService;

    @Transactional
    public String pause(String jobName, String jobGroup) throws SchedulerException {
        JobKey key = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(key)) {
            scheduler.pauseJob(key);

            QuartzConfigModel quartzConfigModel = quartzConfigService
                    .findOne(new QuartzConfigIdentity(jobName, jobGroup));
            quartzConfigModel.setJobStatus(INVALID);
            quartzConfigService.save(quartzConfigModel);
            return "ok";
        } else {
            return "任务不存在";
        }
    }

    @Transactional
    public String resume(String jobName, String jobGroup) throws SchedulerException {
        JobKey key = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(key)) {
            scheduler.resumeJob(key);

            QuartzConfigModel quartzConfigModel = quartzConfigService
                    .findOne(new QuartzConfigIdentity(jobName, jobGroup));
            quartzConfigModel.setJobStatus(VALID);
            quartzConfigService.save(quartzConfigModel);
            return "ok";
        } else {
            return "任务不存在";
        }
    }

    public String triggerJob(String jobName, String jobGroup) throws Exception {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.triggerJob(jobKey);
            return "ok";
        } else {
            return "任務不存在";
        }
    }

    @Transactional
    public String addjob(String jobName, String jobGroup, String cronExpression, String jobDescription,
                         String taskParameter) throws Exception {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        boolean isExists = scheduler.checkExists(jobKey);
        if (isExists) {
            return "已存在";
        }

        startJob(jobName, jobGroup, jobDescription, taskParameter, cronExpression);

        saveQuartzConfigMoel(jobName, jobGroup, cronExpression, jobDescription, taskParameter);

        return null;
    }

    @Transactional
    public String deleteJob(String jobName, String jobGroup) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        // ‘停止触发器
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            // ’ 移除触发器
            scheduler.unscheduleJob(triggerKey);
        }

        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // ‘删除任务
            scheduler.deleteJob(jobKey);
            quartzConfigService.delete(new QuartzConfigIdentity(jobName, jobGroup));
            return "ok";
        } else {
            return "任务不存在";
        }

    }



    public void startJob(String jobName, String jobGroup, String description, String taskParameter,
                         String cronExpression) throws Exception {

        Class cls = Class.forName(JOBCLASSNAME);
        cls.newInstance();

        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, jobGroup).withDescription(description)
                .build();
        jobDetail.getJobDataMap().put("data", taskParameter.split(","));

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).startNow()
                .withSchedule(cronScheduleBuilder).build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void saveQuartzConfigMoel(String jobName, String jobGroup, String cronExpression, String jobDescription,
                                      String taskParameter) {
        QuartzConfigModel quartzConfigModel = new QuartzConfigModel();

        QuartzConfigIdentity id = new QuartzConfigIdentity(jobName, jobGroup);
        quartzConfigModel.setId(id);

        quartzConfigModel.setCronExpression(cronExpression);
        quartzConfigModel.setJobDescription(jobDescription);
        quartzConfigModel.setTaskParameter(taskParameter);

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        quartzConfigModel.setCreateUser(getHttpRequestUserGid(req));

        quartzConfigService.save(quartzConfigModel);
    }

    /**
     * 获取token中的用户
     *
     * @param request HttpServletRequest情報
     * @return
     */
    private String getHttpRequestUserGid(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return "";
        } else {
            String userName = principal.getName();
            if (userName != null && userName.indexOf("@") > 0) {
                return userName.split("@")[0];
            } else {
                return userName;
            }
        }
    }
}
