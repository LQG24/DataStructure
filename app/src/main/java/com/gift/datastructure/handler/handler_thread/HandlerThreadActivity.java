package com.gift.datastructure.handler.handler_thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gift.datastructure.R;
/**
 * CustomHandlerThread 工作线程，定时轮询
 */
public class HandlerThreadActivity extends AppCompatActivity {
    public static final int TIME_MESSAGE_ID = 0x1111;
    private CustomHandlerThread mCustomHandlerThread;
    private Handler mWorkHandler;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        mCustomHandlerThread = new CustomHandlerThread("customHandlerThread");
        mCustomHandlerThread.start();
        mWorkHandler = new Handler(mCustomHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                System.out.println("Thread name:"+Thread.currentThread().getName()+",num:"+i);

                if(msg.what == TIME_MESSAGE_ID){
                    i++;
                    Log.i("lqg" , Thread.currentThread().getName() + ",num:" + i);
                    mWorkHandler.removeMessages(TIME_MESSAGE_ID);
                    mWorkHandler.sendEmptyMessageDelayed(TIME_MESSAGE_ID,1000);
                }

                return true;
            }
        });
        mWorkHandler.sendEmptyMessageDelayed(TIME_MESSAGE_ID,1000);
    }

    @Override
    protected void onDestroy() {
        mCustomHandlerThread.quit();
        mWorkHandler.removeMessages(TIME_MESSAGE_ID);
        mWorkHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}