package com.example.a81903.practice.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a81903.practice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4Fragment extends Fragment {


    public Tab4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JSONObject jsonObject = new JSONObject();



        return inflater.inflate(R.layout.fragment_tab4, container, false);

    }

    private void getDataJSONObject(JSONObject jsonObject){

        int getInt;
        int optInt;
        int fallbackInt;
        try{
            getInt = jsonObject.getInt("int");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    private JSONObject createJSON(){
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("boolean",true)
                    .put("double",10.5)
                    .put("int",100)
                    .put("long",18000305032230531L)
                    .put("string","string")
                    .put("object",createJSONObject(5))
                    .put("array" , createJSONArray(5))
                    .putOpt(null, JSONObject.NULL)
                    .put("put null", JSONObject.NULL)
                    .put("array" , createJSONArray(5))
                    .accumulate("accumulate" , 1)
                    .accumulate("accumulate" , 2)
                    .accumulate("accumulate" , 3);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON","onCreate:" + jsonObject.toString());
        return jsonObject;
    }

    private JSONObject createJSONObject(int count){
        JSONObject jsonObject = new JSONObject();
        try {
            for (int i = 0 ; i < count ; i ++){
                jsonObject.put("JSON_OBJECT_"+ i , i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONArray createJSONArray(int count){
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0 ; i < count ; i ++){
                jsonArray.put(i,i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
