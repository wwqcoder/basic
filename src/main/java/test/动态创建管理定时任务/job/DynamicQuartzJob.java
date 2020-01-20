package test.动态创建管理定时任务.job;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 动态定时任务Job
 *
 * @author linrx1
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution // 不允许并发执行
public class DynamicQuartzJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(DynamicQuartzJob.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        // use JobDetailImpl replace JobDetail for get jobName
        JobDetailImpl jobDetail = (JobDetailImpl) jobexecutioncontext.getJobDetail();
        String name = jobDetail.getName();
        if (StringUtils.isEmpty(name)) {
            throw new JobExecutionException("can not find service info, because desription is empty");
        }

        String[] serviceInfo = name.split("\\.");
        // serviceInfo[0] is JOB_NAME_PREFIX
        String beanName = serviceInfo[1];
        // methodName & modelName
        String methodName = serviceInfo[2];
        Object beanNameContext = applicationContext.getBean(beanName);
        Object modelNameContext = applicationContext.getBean(methodName);
        // method parameter
        JobDataMap dataMap = jobDetail.getJobDataMap();

        try {
            Class beanNameCls = beanNameContext.getClass();
            Class modelNameCls = modelNameContext.getClass();

            Object object = JSONObject.toBean(JSONObject.fromObject(dataMap.get("data")), modelNameCls);

            Class<?>[] parameterTypes = null;
            Method[] methods = beanNameCls.getMethods();
            Parameter[] invokeMethodParams;
            // the array size may be not enough

            for (Method n : methods) {
                if (methodName.equals(n.getName())) {
                    parameterTypes = n.getParameterTypes();
                    invokeMethodParams = n.getParameters();
                    Object[] invokeParam = new Object[invokeMethodParams.length];
                    for (int i = 0; i < invokeMethodParams.length; i++) {
                        String parameterName = invokeMethodParams[i].getName();
                        Field field = modelNameCls.getDeclaredField(parameterName);
                        field.setAccessible(true);
                        invokeParam[i] = field.get(object);
                    }
                    Method method = beanNameCls.getMethod(methodName, parameterTypes);
                    method.invoke(beanNameCls.newInstance(), invokeParam);
                }
            }

            logger.info("dynamic invoke {}.{}()", beanNameContext.getClass().getName(), methodName);
        } catch (Exception e) {
            logger.error("reflect invoke service method error", e);
        }

    }

}
