package com.gift.datastructure.handler.count_down;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.datastructure.R;
import com.gift.datastructure.handler.handler.WeakHandler;

import java.util.Arrays;
import java.util.List;
/**
 * 自定义倒计时的View，内部用弱引用持有回调和倒计时的View
 *
 * 当有强引用指向value内存区域时，即使进行gc，弱引用也不会被释放，对象不回被回收。
 * 当无强引用指向value内存区域是，此时进行gc，弱引用会被释放，对象将会执行回收流程。
 */
public class CountDownActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Long> timeList;
    private CustomAdapter mCustomAdapter;
    private WeakHandler mWeakHandler = new WeakHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        timeList = Arrays.asList((long)10*1000,(long)20*1000,(long)30*1000,(long)40*1000,(long)1000*1000);
        mCustomAdapter = new CustomAdapter(this,timeList);
        mRecyclerView.setAdapter(mCustomAdapter);
        mWeakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
             System.gc();
            }
        },5000);
    }


    private class ViewHolder extends RecyclerView.ViewHolder{
        private CountDownView mCountDownView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCountDownView = itemView.findViewById(R.id.count_down_text);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<ViewHolder>  {

        private List<Long> mList;
        private Context mContext;
        public CustomAdapter(Context context,List<Long> timeList) {
            mContext = context;
            mList = timeList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_count_down,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            holder.mCountDownView.setVisibility(View.VISIBLE);
            holder.mCountDownView.setCountDownTime(mList.get(position));
            holder.mCountDownView.start();
            holder.mCountDownView.setCountDownListener(new CountDownView.CountDownListener() {
                @Override
                public void onFinish() {
                    Log.i("lqg","onFinish");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


//        @Override
//        public void onFinish() {
//            Log.i("lqg","onFinish");
//        }
    }

}