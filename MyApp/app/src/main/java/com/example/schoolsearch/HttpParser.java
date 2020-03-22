package com.example.schoolsearch;

import android.app.job.JobScheduler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public void parseJson(String jsonString) throws JSONException {

        Map<String,String> map = new HashMap<String ,String >();

        //todo パースする時に使う
        JSONObject jsonObject = new JSONObject(jsonString);




    }

}
