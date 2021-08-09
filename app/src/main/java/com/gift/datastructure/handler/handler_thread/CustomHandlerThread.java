package com.gift.datastructure.handler.handler_thread;

import android.os.HandlerThread;

public class CustomHandlerThread extends HandlerThread {

    public CustomHandlerThread(String name) {
        super(name);
    }

    public CustomHandlerThread(String name, int priority) {
        super(name, priority);
    }
}
