package test.定时.demo03.demo0303;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述 代码动态添加
 */
@Component
public class Task {
    @Autowired
    private ThreadPoolTaskScheduler myScheduler;

    public void addJob(){

        myScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " run ");
            }
        } , new CronTrigger("0/5 * *  * * ? ")); //每5秒执行一次
    }
}
