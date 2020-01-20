package test.定时.demo01;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述  JDK 自带的定时器实现
 */
public class ScheduleDemo {

    /**
     *  schedule(TimerTask task, long delay) 延迟 delay 毫秒 执行
     */
    public static void schedule01(){
        for (int i = 0; i < 10; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " run ");
                }
            }, 1000);
        }
    }

    /**
     * schedule(TimerTask task, Date time) 特定時間執行
     */
    public static void schedule02(){
        for (int i = 0; i < 10; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " run ");
                }
            }, new Date(System.currentTimeMillis() + 2000));
        }
    }

    /**
     * schedule(TimerTask task, long delay, long period) 延迟 delay 执行并每隔period 执行一次
     */
    public static void schedule03(){
        for (int i = 0; i < 10; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " run ");
                }
            }, 2000 , 3000);
        }
    }

    public static void schedule04(){
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 10; ++i) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " run ");
                }
            } , 2 , TimeUnit.SECONDS);
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        schedule01();
        schedule02();
        schedule03();
        schedule04();
    }
}
