package test.定时任务.demo01;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述  定时任务执行类--xml文件配置
 */
public class TestQuartzCluster implements Job, Serializable {
    private static final long serialVersionUID = -6605766126594260961L;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("#################################:TestQuarzCluster");
    }
}
