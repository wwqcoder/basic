package cn.wwq.thread;

public class CpDemo {
    //商品库存
    private static int storeMount = 0;
    //监视器对应的对象
    private static Object monitor = new Object();
    //生产者线程
    static class ProducerThread implements Runnable{

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void produce() throws InterruptedException {
            while (true){
                synchronized (monitor){
                    //循环检测库存是否大于0，大于0表示还有商品可以消费，线程等待消费者消费商品
                    while (storeMount > 0){
                        monitor.wait();
                    }
                    //被唤醒后获取到对象的监视器之后执行的代码
                    System.out.println("Thread "+Thread.currentThread().getName()+"开始生产了。。。");
                    //生产商品
                    storeMount = 1;
                    //唤醒消费者
                    monitor.notify();
                    Thread.sleep(1000);
                }
            }
        }
    }
    //消费者线程
    static class ConsumerThread implements Runnable{

        @Override
        public void run() {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void consumer() throws InterruptedException {
            while (true){
                synchronized (monitor){
                    //检测库存是否不为0，如果不为0，那么有商品可供消费，否则等待生产者生产商品
                    while (storeMount == 0){
                        monitor.wait();
                    }
                    //消费商品
                    storeMount = 0;
                    //唤醒生产者
                    monitor.notify();
                    System.out.println("Thread "+Thread.currentThread().getName()+"开始消费了。。。");
                    Thread.sleep(1000);
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(new ProducerThread(), "ProducerThread");
        Thread consumerThread = new Thread(new ConsumerThread(), "ConsumerThread");
        producerThread.start();
        consumerThread.start();
    }

    /*
       火车票卖票
    *static class Thread1 implements Runnable{

        Object obj = new Object();
        int ticket = 100;

        @Override
        public void run() {
            while (ticket > 0){
                synchronized (obj){
                    System.out.println(Thread.currentThread().getName()+"----"+ticket);
                    ticket--;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    * */
    //线程池方案
    /*ExecutorService executorService = Executors.newFixedThreadPool(100);
        while(true){
        executorService.execute(()->{
            System.out.println("王玮琦哈哈");
        });
        Thread.sleep(3000);
    }*/

}
