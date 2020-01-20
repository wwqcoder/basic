package test.线程池;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public class FixedThreadDemo {
    public static void main(String args[]) {
        // 创建任务对象
        MyTask task = new MyTask();
        // 创建固定数量线程池
        ExecutorService es = Executors.newFixedThreadPool(5);
        for(int i=0; i<10; i++) {
            // 向线程池里提交任务
            es.submit(task);
        }
    }

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread ID: " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
