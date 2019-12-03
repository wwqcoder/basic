package cn.wwq.threadpool;

import java.util.LinkedList;

public class RunnableTaskQueue {

    private final LinkedList<Runnable> tasks = new LinkedList<>();

    public Runnable getTask() throws InterruptedException {
        synchronized (tasks){
            if (tasks.isEmpty()){
                System.out.println(Thread.currentThread().getName() + "说：此刻队列为空，需要等待！！！");
                tasks.wait();
            }
            return tasks.removeFirst();
        }
    }

    public void addTask(Runnable runnable){
        synchronized (tasks){
            tasks.add(runnable);
            tasks.notifyAll();
        }
    }
}
