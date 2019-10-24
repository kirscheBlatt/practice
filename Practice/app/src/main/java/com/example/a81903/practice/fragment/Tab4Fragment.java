package com.example.a81903.practice.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a81903.practice.R;

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
                    .put("object",createJSONObject
                    )
        }
    }

}
