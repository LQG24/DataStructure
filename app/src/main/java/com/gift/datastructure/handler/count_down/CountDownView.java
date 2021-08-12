package com.gift.datastructure.handler.count_down;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gift.datastructure.R;

import java.lang.ref.WeakReference;


public class CountDownView extends androidx.appcompat.widget.AppCompatTextView {
    private float countDownTime;
    public static final int MSG_ID = 6666;
    private InnerHandler mInnerHandler = new InnerHandler(this);
    private CountDownListener mCountDownListener;

    public CountDownView(@NonNull Context context) {
        this(context, null, 0);
    }

    public CountDownView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setCountDownTime(long countDownTime) {
        this.countDownTime = countDownTime;
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        mCountDownListener = countDownListener;
        mInnerHandler.setCountDownListener(mCountDownListener);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        countDownTime = array.getInteger(R.styleable.CountDownView_count_down, 0);
        array.recycle();
//        updateTimeText();
    }

    private void updateTimeText() {
        if(countDownTime < 0 || countDownTime ==0){
            this.setText(String.valueOf(0));
        }else {
            this.setText(getTime((long) countDownTime));
        }

    }

    public void start() {
        mInnerHandler.sendEmptyMessageDelayed(MSG_ID, 1000);
    }

    public void stop() {
        mInnerHandler.removeMessages(MSG_ID);
        mInnerHandler.removeCallbacksAndMessages(null);
    }

    public interface CountDownListener {
        void onFinish();
    }

    private static class InnerHandler extends Handler {
        private WeakReference<CountDownView> mWeakReference;
        private WeakReference<CountDownListener> mWeakReferenceListener;

        public InnerHandler(CountDownView countDownView) {
            mWeakReference = new WeakReference<>(countDownView);
        }

        public void setCountDownListener(CountDownListener countDownListener) {
            mWeakReferenceListener = new WeakReference<>(countDownListener);
        }

        private CountDownView getView() {
            if (mWeakReference != null) {
                return mWeakReference.get();
            }
            return null;
        }

        private void onFinish() {
            if (mWeakReferenceListener != null && mWeakReferenceListener.get() != null) {
                mWeakReferenceListener.get().onFinish();
            }
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (getView() == null) {
                return;
            }
            if (getView().countDownTime <= 1000) {
                getView().countDownTime = 0;
                removeMessages(MSG_ID);
                onFinish();
            } else {
                getView().countDownTime -= 1000;
                removeMessages(MSG_ID);
                sendEmptyMessageDelayed(MSG_ID, 1000);
            }
            getView().updateTimeText();

        }


    }

    private static final int ONE_HOUR = 60 * 60 * 1000;
    private static final int ONE_MINUTE = 60 * 1000;
    private static final int ONE_SECOND = 1000;

    private String getTime(long millisecond) {
        int hour = -1;
        int minute = -1;
        int second = -1;
        if (millisecond >= ONE_HOUR) {
            hour = (int) (millisecond / ONE_HOUR);
            millisecond = millisecond % ONE_HOUR;
        }

        if (millisecond >= ONE_MINUTE) {
            minute = (int) (millisecond / ONE_MINUTE);
            millisecond = millisecond % ONE_MINUTE;
        }

        if (millisecond >= ONE_SECOND) {
            second = (int) (millisecond / ONE_SECOND);
        }

        return (hour != -1 ? hour + ":" : "") + (minute != -1 ? minute + ":" : "") + second;
    }
}
