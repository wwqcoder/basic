package cn.wwq.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MyExecutor {
    private final int poolSize;

    private final RunnableTaskQueue runnableTaskQueue;

    private final List<Thread> threads = new ArrayList<>();

    public MyExecutor(int poolSize) {
        this.poolSize = poolSize;
        this.runnableTaskQueue = new RunnableTaskQueue();
        Stream.iterate(1,item -> item + 1).limit(poolSize).forEach(item -> {
            initThread();
        });
    }

    private void initThread() {
        if (threads.size() <= poolSize){
            Thread thread = new Thread(() -> {
                while (true){
                    try {
                        Runnable task = runnableTaskQueue.getTask();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void execute(Runnable runnable){
        runnableTaskQueue.addTask(runnable);
    }


}
