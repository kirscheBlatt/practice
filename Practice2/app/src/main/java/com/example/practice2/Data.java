package com.example.practice2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {

    List<Map<String, Object>> personalDataList = new ArrayList<>();

    public void setPersonalDataList(List<Map<String, Object>> personalDataList) {
        this.personalDataList = personalDataList;
    }

    public List<Map<String, Object>> getPersonalDataList() {
        return personalDataList;
    }

    private static final Data data = new Data();
    public static Data getInstance() {
        return data;
    }

    //todo 後でｊそんファイルにする

    //    private JSONObject createJSON(){
//       JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name",editText.getText().toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }

}
