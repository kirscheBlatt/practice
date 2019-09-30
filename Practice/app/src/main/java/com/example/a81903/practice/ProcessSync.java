package com.example.a81903.practice;

import android.net.sip.SipSession;
import android.view.View;

import java.util.Date;
import java.util.logging.LogManager;

public final class ProcessSync {
    private static final long TIME_SPAN_BLOCKING_MILLISECONDS = 200L;
    private static String TAG = "ProcessSync";
    private static ProcessSync s_Instance = null;

    private long mTimestamp = 0;

    private ProcessSync(){
    }

    private static ProcessSync getInstance(){
        return s_Instance;
    }

    private static void preparationInstance(){
if (s_Instance==null){
    s_Instance =new ProcessSync();
}
    }

    public static View.OnClickListener tapCheck(
            final View.OnClickListener listener
    ){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getInstance().check()){
                    return;
                }
                listener.onClick(v);
            }
        };
    }
    private synchronized boolean check (){
        final long timestamp = new Date().getTime();
        if (timestamp-mTimestamp >= TIME_SPAN_BLOCKING_MILLISECONDS){
            mTimestamp = timestamp;
            return true;
        }else {
            return false;
        }
    }
    public synchronized ProcessSync clear(){
        mTimestamp=0;
        return this;
    }
}
