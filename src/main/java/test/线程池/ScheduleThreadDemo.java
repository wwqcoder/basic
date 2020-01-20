package test.线程池;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public class ScheduleThreadDemo {
    public static void main(String args[]) {
        // 创建任务对象
        MyTask task = new MyTask();
        // 创建任务调度计划对象
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(100);
        // 设置任务与执行计划
        ses.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()/1000 + ": Thread ID: " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
