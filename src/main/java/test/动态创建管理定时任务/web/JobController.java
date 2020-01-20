package test.动态创建管理定时任务.web;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import test.动态创建管理定时任务.dynamicquery.service.QuartzConfigService;
import test.动态创建管理定时任务.entity.ResponseData;
import test.动态创建管理定时任务.request.*;
import test.动态创建管理定时任务.service.JobService;

@RestController
public class JobController {
    private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Resource
    Scheduler scheduler;

    @Resource
    QuartzConfigService quartzConfigService;

    @Resource
    JobService jobService;

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody AddJobRequest req) throws Exception {
        LOGGER.info("add quartz job");

        String result = jobService.addjob(req.getJobName(), req.getJobGroup(), req.getCronExpression(),
                req.getJobDescription(), req.getTaskParameter());
        return ResponseData.ok(result);
    }

    @PostMapping("/list")
    public ResponseEntity<?> findAllJob(@RequestBody FindAllJobRequest req) {
        LOGGER.info("任务列表");
        Page page = quartzConfigService.findAll(PageRequest.of(req.getPageNo(), req.getPageSize()));
        return ResponseData.ok(page);
    }

    @PostMapping("/trigger")
    public ResponseEntity<?> trigger(@RequestBody TriggerRequest req) throws Exception {
        LOGGER.info("触发任务");
        String result = jobService.triggerJob(req.getJobName(), req.getJobGroup());
        return ResponseData.ok(result);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody RemoveJobRequest req) throws SchedulerException {
        LOGGER.info("移除任务");
        String result = jobService.deleteJob(req.getJobName(), req.getJobGroup());
        return ResponseData.ok(result);
    }

    @PostMapping("/pause")
    public ResponseEntity<?> pause(@RequestBody PauseAndResumeRequest req) throws SchedulerException {
        LOGGER.info("停止任务");
        String result = jobService.pause(req.getJobName(), req.getJobGroup());
        return ResponseData.ok(result);
    }

    @PostMapping("/resume")
    public ResponseEntity<?> resume(@RequestBody PauseAndResumeRequest req) throws SchedulerException {
        LOGGER.info("恢复任务");
        String result = jobService.resume(req.getJobName(), req.getJobGroup());
        return ResponseData.ok(result);
    }
}
