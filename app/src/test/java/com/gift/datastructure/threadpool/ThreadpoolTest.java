package com.gift.datastructure.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/u010687392/article/details/49850803
 *
 *
 *获取这五种线程池，通过Executors的工厂方法来获取：
 *
 *     ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
 *     ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
 *     ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
 *     ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
 *     ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
 *
 *
 * 1、newFixedThreadPool() ：
 * 作用：该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。
 * 栗子：假如有一个新任务提交时，线程池中如果有空闲的线程则立即使用空闲线程来处理任务，如果没有，则会把这个新任务存在一个任务队列中，一旦有线程空闲了，则按FIFO方式处理任务队列中的任务。
 *
 * 2、newCachedThreadPool() ：
 * 作用：该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。即该线程池中的线程数量不确定，是根据实际情况动态调整的。
 * 栗子：假如该线程池中的所有线程都正在工作，而此时有新任务提交，那么将会创建新的线程去处理该任务，而此时假如之前有一些线程完成了任务，现在又有新任务提交，那么将不会创建新线程去处理，而是复用空闲的线程去处理新任务。那么此时有人有疑问了，那这样来说该线程池的线程岂不是会越集越多？其实并不会，因为线程池中的线程都有一个“保持活动时间”的参数，通过配置它，如果线程池中的空闲线程的空闲时间超过该“保存活动时间”则立刻停止该线程，而该线程池默认的“保持活动时间”为60s。
 *
 * 3、newSingleThreadExecutor() ：
 * 作用：该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
 *
 * 4、newScheduledThreadPool() ：
 * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
 *
 * 5、newSingleThreadScheduledExecutor() ：
 * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小。
 *
 */
public class ThreadpoolTest {
    
    //线程优先级执行
    @Test
    public void Test() {
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            final int priority = i;
            executorService.execute(new PriorityRunnable(priority) {
                @Override
                protected void doSth() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("thread name:" + threadName + ",priority:" + priority + ",task");
                }
            });
            
        }
    }
    
}
