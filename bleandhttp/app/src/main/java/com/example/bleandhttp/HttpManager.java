package com.example.bleandhttp;

import android.os.Handler;
import android.os.HandlerThread;

public class HttpManager {

    private HttpManager(){}

    private static class SingletonHolder {

        private static final HttpManager instance = new HttpManager();

    }

    public static HttpManager getInstance() {

        return SingletonHolder.instance;

    }



    static String ret;


    public void  requestGet(){


        final HttpTask task = new HttpTask(){
            @Override
            public void run() {
                super.run();
            }
        };
        HandlerThread handlerThread = new HandlerThread("hoge");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(task);

    }


}

