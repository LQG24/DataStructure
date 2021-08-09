package com.gift.datastructure.handler.handler_thread;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.junit.Test;

public class HandlerThreadTest {
    public static final int TIME_MESSAGE_ID = 11;
    private CustomHandlerThread mCustomHandlerThread;
    private Handler mWorkHandler;
    private int i = 0;
    @Test
    public void test(){
        mCustomHandlerThread = new CustomHandlerThread("customHandlerThread");
        mCustomHandlerThread.start();
        mWorkHandler = new Handler(mCustomHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                System.out.println("Thread name:"+Thread.currentThread().getName()+",num:"+i);

                if(msg.what == TIME_MESSAGE_ID){
                    i++;
                    System.out.println("Thread name:"+Thread.currentThread().getName()+",num:"+i);
                    mWorkHandler.removeMessages(TIME_MESSAGE_ID);
                    mWorkHandler.sendEmptyMessageDelayed(TIME_MESSAGE_ID,1000);
                }

                return true;
            }
        });
        mWorkHandler.sendEmptyMessageDelayed(TIME_MESSAGE_ID,1000);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
