package test.Object;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/24
 * @描述
 */
public class ObjectWaitTest1 {
    static Object lock = new Object();
    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        ExecutorService executeService = Executors.newCachedThreadPool(new MyThreadFactory());
        for (int i = 0; i < 10; i++) {
            executeService.submit(() -> {
                synchronized (lock) {
                  //if (lock1.tryLock()){
                      try {
                          System.out.println(Thread.currentThread().getName() + " begin");
                          lock.wait(1000 * 30); // wiat 30s
                      } catch (Exception e) {
                          e.printStackTrace();
                      }

                      System.out.println(Thread.currentThread().getName() + " finish");
                 // }
                  //lock1.unlock();

                }
            });
        }

        executeService.shutdown();
    }


    static class MyThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "mythiread - " + threadNumber.getAndIncrement());
        }
    }
}
