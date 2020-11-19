package com.gift.datastructure;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * *
 * * ，这三个方法分别都会在任务执行前调用、任务执行完成后调用、线程池关闭后调用。
 * *
 * *public class MyThreadPoolExecutor extends ThreadPoolExecutor {
 * *     public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
 * *     BlockingQueue<Runnable> workQueue) {
 * *         super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
 * *     }
 * *
 * *     @Override
 * *     protected void beforeExecute(Thread t, Runnable r) {
 * *         super.beforeExecute(t, r);
 * *         String threadName = t.getName();
 * *         Log.v("zxy", "线程：" + threadName + "准备执行任务！");
 * *     }
 * *
 * *     @Override
 * *     protected void afterExecute(Runnable r, Throwable t) {
 * *         super.afterExecute(r, t);
 * *         String threadName = Thread.currentThread().getName();
 * *         Log.v("zxy", "线程：" + threadName + "任务执行结束！");
 * *     }
 * *
 * *     @Override
 * *     protected void terminated() {
 * *         super.terminated();
 * *         Log.v("zxy", "线程池结束！");
 * *     }
 * * }
 *
 *虽说线程池极大改善了系统的性能，不过创建线程池也是需要资源的，所以线程池内线程数量的大小也会影响系统的性能，
 * 大了反而浪费资源，小了反而影响系统的吞吐量，所以我们创建线程池需要把握一个度才能合理的发挥它的优点，
 * 通常来说我们要考虑的因素有CPU的数量、内存的大小、并发请求的数量等因素，按需调整。
 *
 *
 * 通常核心线程数可以设为CPU数量+1，而最大线程数可以设为CPU的数量*2+1。
 *
 * Runtime.getRuntime().availableProcessors();
 */
public class PauseThreadPoolExecutor extends ThreadPoolExecutor {
    
    private boolean isPaused;
    
    private ReentrantLock pauseLock = new ReentrantLock();
    
    private Condition mCondition = pauseLock.newCondition();
    
    public PauseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                mCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }
    
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
        
    }
    
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            mCondition.signalAll();
        } finally {
            pauseLock.unlock();
        }
        
    }
    
}
