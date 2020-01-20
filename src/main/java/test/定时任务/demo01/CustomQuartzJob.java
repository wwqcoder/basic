package test.定时任务.demo01;

import org.apache.catalina.core.ApplicationContext;
import org.quartz.*;

import java.io.Serializable;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述
 */
public class CustomQuartzJob implements Job, Serializable {
    private static final long serialVersionUID = -6605766126594260962L;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
            // applicationContext.getBean("BeanName");//获取spring bean实例
            JobDataMap dataMap = context.getMergedJobDataMap();
            String params = dataMap.getString("params");//获取参数
            System.out.println("**************************:" + params);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
