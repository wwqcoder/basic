package test.线程池;

import java.util.concurrent.*;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public class ThreadPoolDemo {
    public static void main(String args[]) {
        // 创建任务对象
        MyTask task = new MyTask();
        // 获取自定义线程池
        ExecutorService es = getMyThreadPool();
        for(int i=0; i<20; i++) {
            // 向线程池提交任务
            es.submit(task);
        }
    }

    /**
     * public ThreadPoolExecutor(
     *     int corePoolSize,       // todo:指定线程池中线程的数量（总线程量可大于等于这个值）
     *     int maximumPoolSize,    // todo:指定线程池中最大线程数量（总线程量不可能超越这个数值）
     *     long keepAliveTime,     // 超过corePoolSize数量的空闲线程，存活的时间
     *     TimeUtil unit,          // keepAliveTime的单位
     *     BlockingQueue<Runnable> workQueue,    // 任务队列，被提交但为执行的任务
     *     ThreadFactory threadFactory,          // 线程工厂，用于创建线程
     *     RejectedExecutionHandler handler      // 当workQueue队列满的时候的拒绝策略
     *     )
     * @return
     */
    // 自定义线程池，我们创建一个线程数固定的线程池
    public static ExecutorService getMyThreadPool() {
        ExecutorService es = new ThreadPoolExecutor(
                // 设置线程池大小
                5, 10, 0L, TimeUnit.MILLISECONDS,
                // 设置缓存队列
                new LinkedBlockingQueue<Runnable>(5),
                // 设置线程工厂
                Executors.defaultThreadFactory(),
                // 设置拒绝策略
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discard! ");    // 输出日志后直接丢弃任务
                    }
                });
        return es;
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
