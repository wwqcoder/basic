package cn.wwq.threadpool;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Client2 {
    public static void main(String[] args) {
        MyExecutor executor = new MyExecutor(5);

        Stream.iterate(1, item -> item + 1).limit(10).forEach(
                item -> {
                    try {
                        if(item%2==0){
                            TimeUnit.SECONDS.sleep(2);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    executor.execute(() -> {
                        System.out.println(Thread.currentThread().getName() + " execute this task");

                    });
                }
        );
    }
}
