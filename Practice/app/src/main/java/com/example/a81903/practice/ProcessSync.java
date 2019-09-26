package com.example.a81903.practice;

import android.view.View;

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
                
            }
        }
    }
}
