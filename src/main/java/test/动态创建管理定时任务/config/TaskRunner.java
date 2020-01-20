package test.动态创建管理定时任务.config;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.util.List;
import javax.annotation.Resource;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import test.动态创建管理定时任务.dynamicquery.service.QuartzConfigService;
import test.动态创建管理定时任务.entity.QuartzConfigModel;
import test.动态创建管理定时任务.service.JobService;

/**
 * 启动时扫描所有的有效定时任务并启动
 */
@Component
public class TaskRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);

    private final Integer VALID = 0;

    @Resource
    Scheduler scheduler;

    @Resource
    QuartzConfigService quartzConfigService;

    @Resource
    JobService jobService;

    @Override
    public void run(ApplicationArguments var) throws Exception {
        LOGGER.info("初始化任务");
        List<QuartzConfigModel> quartzs = quartzConfigService.findByCondition(null, null, VALID);
        if (!CollectionUtils.isEmpty(quartzs)) {
            quartzs.forEach(n -> {
                try {
                    jobService.startJob(n.getId().getJobName(), n.getId().getJobGroup(), n.getJobDescription(),
                            n.getTaskParameter(), n.getCronExpression());
                } catch (Exception e) {
                    LOGGER.error("初始化任务失败");
                }
            });
        }
    }

}
