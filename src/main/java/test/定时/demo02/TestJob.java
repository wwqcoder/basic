package test.定时.demo02;

import cn.hutool.core.date.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述
 */
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + " test job begin " + System.currentTimeMillis());
    }
}
