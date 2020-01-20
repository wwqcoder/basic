package test.线程池;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
public class ForkJoinDemo extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10000;    // 门阀值，当大于这个值才进行拆分处理
    private long start;                             // 数列的起始值
    private long end;                               // 数列的结束值

    public ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    // 对数列进行求和
    @Override
    protected Long compute() {
        // 定义求和对象
        long sum = 0;
        if((end - start) < THRESHOLD) {
            // 当求和数列的数量小于门阀值，则直接计算不需要分拆
            for(long i=start; i<=end; i++) {
                sum += i;
            }
        }
        else {
            // 当求和数列的数量大于门阀值，则拆分成100个小任务
            ArrayList<ForkJoinDemo> subTasks = new ArrayList<ForkJoinDemo>();
            long step = (start + end) / 100;    // 计算每个小任务的数列的数量
            long firstOne = start;              // 动态记录小任务数列的起始值
            // 将任务拆分，并提交给框架
            for(int i=0; i<100; i++) {
                long lastOne = firstOne + step; // 动态记录小任务数列的结束值
                if(lastOne > end) {
                    // 当队列结束值大于end，需要将lastOne设置为end
                    lastOne = end;
                }
                ForkJoinDemo subTask = new ForkJoinDemo(firstOne, lastOne);
                firstOne = firstOne + step + 1;
                // 将子任务添加到数组中
                subTasks.add(subTask);
                // 执行子任务，这里是将子任务交个Fork/Join框架，什么时候开始执行由框架自己决定
                subTask.fork();
            }
            // 将子任务的计算结果汇总
            for(ForkJoinDemo st : subTasks) {
                sum += st.join();
            }
        }
        return sum;
    }

    public static void main(String args[]) {
        // 创建任务对象
        ForkJoinDemo task = new ForkJoinDemo(0, 500000L);
        // 创建Fork/Join线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 将任务提交给线程池
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            // 取出运算结果
            long sum = result.get();
            System.out.println("sum: " + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
