package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class Manager {

HandlerThread mHandlerThread;
    public Manager() {
        mHandlerThread = new HandlerThread("hoge");
    }

    public String request(){

        String ret = "";
        mHandlerThread.start();
        Handler handler = new Handler(mHandlerThread.getLooper());
        handler.post(new Task() {
            @Override
            public void run() {
                super.run();
            }
        });



        return ret;
    }

}
