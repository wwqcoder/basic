package test.lock;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/5
 * @描述
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内的共享变量
 *
 * 三个模块共享数据，主线程模块和AB模块
 *
 * @author Administrator
 *
 */
public class ThreadScopeShareData {
    // 准备共享的数据
    private static int data = 0;
    // 存放各个线程对应的数据
    private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        // 启动两个线程
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    // 现在当前线程中修改一下数据,给出修改信息
                    int data = new Random().nextInt();
                    // 将线程信息和对应数据存储起来
                    threadData.put(Thread.currentThread(), data);
                    System.out.println(Thread.currentThread().getName() + " has put data :" + data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }

    static class B {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }
}