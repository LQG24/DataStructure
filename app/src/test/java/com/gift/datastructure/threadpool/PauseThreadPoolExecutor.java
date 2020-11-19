package com.gift.datastructure.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
        while (isPaused) {
            try {
                mCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pauseLock.unlock();
            }
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
