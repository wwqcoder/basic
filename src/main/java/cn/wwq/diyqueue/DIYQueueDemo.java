package cn.wwq.diyqueue;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DIYQueueDemo {

    private final static Queue<String> queue = new DIYQueue<>();

    class Product implements Runnable{

        private final String message;

        public Product(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            
            try {
                Boolean success = queue.put(message);
                if (success){
                    System.out.println("放入的信息为："+message);
                    return;
                }
                System.out.println("失败放入的信息为："+message);
            }catch (Exception e){
                System.out.println("失败放入的信息为："+message);
            }

        }
    }

    class Consumer implements Runnable{

        @Override
        public void run() {
            try {
                String message =(String)queue.take();
                System.out.println("消费的消息为："+message);
            } catch (Exception e) {
                System.out.println("消费失败的消息为："+e);
            }

        }
    }

    @Test
    public void test() throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0){
                executor.submit(new Product(i+""));
                continue;
            }
            executor.submit(new Consumer());
        }
        Thread.sleep(10000);

    }

}
