package com.gift.datastructure.threadpool;

/**
 * 创建一个实现Runnable接口的类，并向外提供一个抽象方法供我们实现自定义功能，并实现Comparable接口，实现这个接口主要就是进行优先级的比较
 */
public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    
    private int priority;
    
    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }
    
    //a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
    @Override
    public int compareTo(PriorityRunnable o) {
        int my = this.getPriority();
        int other = o.getPriority();
        return Integer.compare(other, my);
    }
    
    @Override
    public void run() {
        doSth();
    }
    
    protected abstract void doSth();
    
    public int getPriority() {
        return priority;
    }
}
