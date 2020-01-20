线程池Executor框架
        Executor采用工厂模式提供了各种类型的线程池，是实际使用中我们就直接从Executor中获取我们想要的线程池，拿来直接使用即可。下面简单的介绍下Executor提供的五大类线程池。

        newFixedThreadPool()方法：该方法返回一个固定数量的线程池；

        newSingleThreadExecutor()方法：该方法返回只有一个线程的线程池；

        newCachedThreadPool()方法：该方法返回一个可根据实际情况调整线程数量的线程池；

        newScheduledThreadPool()方法：该方法返回一个ScheduleExecutorService对象，可以指定线程数量；

        newSingleThreadScheduledExecutor()方法：该方法返回一个ScheduleExecutorService对象，线程数量为1；

        上面线程池分两大类，一类是无计划的任务，提交就会执行，这类业务应用很广泛；另一类是有计划的任务，提交后会按照设定的规则去执行，这种应用的场景相对少一些，不过对有些业务是必须的，比如：我们系统晚上需要清空用户的状态、优惠券到期了自动提醒等等，用到的就是这类计划任务，常见的有spring task。下面分别举例演示两种线程池的使用。

线程池的内部实现

        Executor为我们提供了功能各异的线程池，其实其内部均是由ThreadPoolExecutor实现的，我们详细了解下ThreadPoolExecutor实现原理不但对我们使用理解Executor提供的线程池大有帮助，也让我们能根据实际情况自定义特定的线程池。

        首先介绍下ThreadPoolExecutor类最核心的构造方法，
public ThreadPoolExecutor(  
    int corePoolSize,       // 指定线程池中线程的数量（总线程量可大于等于这个值）  
    int maximumPoolSize,    // 指定线程池中最大线程数量（总线程量不可能超越这个数值）  
    long keepAliveTime,     // 超过corePoolSize数量的空闲线程，存活的时间  
    TimeUtil unit,          // keepAliveTime的单位  
    BlockingQueue<Runnable> workQueue,    // 任务队列，被提交但为执行的任务  
    ThreadFactory threadFactory,          // 线程工厂，用于创建线程  
    RejectedExecutionHandler handler      // 当workQueue队列满的时候的拒绝策略  
    )  

看到corePoolSize和maximumPoolSize的含义，应该很容易通过设置参数的不同，得到Executors提供的线程池对象。该方法一共七个参数，前四个很简单，我们都会使用，第六个一般使用的是JDK默认提供的，剩下的就只有workQueue和handler了。
        workQueue：存放的是提交的任务，例如：es.submit(task);在样例中提交了10次task，但线程只有5个，于是就有5个提交但没开始执行的任务存到了workQueue里啦。既然是一个存放任务的队列，我们知道实现队列的方式有多种，比如：ArrayBlockQueue、LinkedBlockQueue、PriorityBlockQueue等等，选择不同的队列就会带来不同的问题。ArrayBlockQueue，存在一个任务过多超出队列长度；LinkedBlockQueue，接受过多的任务可能会占用太多内存，造成内存崩溃等等。这里介绍下，newFixedThreadPool和newSingleFixedThreadPool使用的都是LinkedBlockQueue，newCacheThreadExecutor使用的SynchronousQueue队列。关于队列的选择是要根据实际情况来确定，这也是自定义线程池的核心。

        handler：拒绝策略，实际上是一种补救措施，就是当超出了workQueue临界值了，我们怎么让我们的系统不至于崩溃。JDK内置的处理方法有AbortPolicy，抛出异常阻止程序（除非是安全性要求极高，否则在大并发情况下使用这种做法不是很明智）；DiscardPolicy，丢弃无法处理的任务（如果允许丢弃，这是不错方案）；DiscardOledesPolicy：也是丢弃任务，只不过丢弃的是队列最前的一个任务。由于上面策略都是实现RejectExecutionHandler接口，我们也可以实现该接口自定义拒绝策略。
        
        

Fork/Join框架

        上面我们详细介绍了线程池的原理，还是那句话，学底层原理是拿来做设计，并不是让直接去使用。Fork/Join在线程池的基础上，做了更近一步的封装，对线程的开启、分发做了优化，使系统更稳定。另外补充下，Fork/Join还涉及到关于多线程的一个重要思想：“分而治之”，通俗的将就是将复杂的问题拆分成多个简单问题，分开处理。下面通过一段样例了解下Fork/Join。
package concurrent.threadpool;  
  
import java.util.ArrayList;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ForkJoinPool;  
import java.util.concurrent.ForkJoinTask;  
import java.util.concurrent.RecursiveTask;  
  
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