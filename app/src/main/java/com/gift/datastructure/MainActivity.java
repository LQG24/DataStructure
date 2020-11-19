package com.gift.datastructure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

/**
 *具有暂停功能的优先级线程池
 *
 *
 */

public class MainActivity extends AppCompatActivity {
    
    PauseThreadPoolExecutor mPauseThreadPoolExecutor;
    
    TextView threadInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadInfo = findViewById(R.id.thread_info_tv);
        mPauseThreadPoolExecutor =
                new PauseThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
    
        Log.i("MainActivity","threads:"+ Runtime.getRuntime().availableProcessors());
    }
    
    public void onStartThread(View v) {
        for (int i = 0; i < 100; i++) {
            final int priority = i;
            mPauseThreadPoolExecutor.execute(new PriorityRunnable(priority) {
                @Override
                protected void doSth() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            threadInfo.setText(String.valueOf(priority));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    
    public void onPauseThread(View view) {
        mPauseThreadPoolExecutor.pause();
    }
    
    public void onResumeThread(View view) {
        mPauseThreadPoolExecutor.resume();
    }
}