package com.gift.datastructure;

import android.os.Build;

import androidx.annotation.RequiresApi;

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    
    private int priority;
    
    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }
    
    //a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
