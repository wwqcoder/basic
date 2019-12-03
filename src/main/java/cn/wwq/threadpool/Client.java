package cn.wwq.threadpool;

import java.util.stream.Stream;

public class Client {
    public static void main(String[] args) {
        MyExecutor executor = new MyExecutor(5);
        Stream.iterate(1,item -> item + 1).limit(10).forEach(
                item ->{
                    executor.execute(() -> {
                        try {
                            System.out.println(Thread.currentThread().getName() + "正在执行这个任务 ！！！");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
        );
    }
}
