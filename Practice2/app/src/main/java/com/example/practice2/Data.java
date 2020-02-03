package com.example.practice2;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * データの入出力とパース
 */

class Data {



    List<Map<String, Object>> personalDataList = new ArrayList<>();
    Map<String , Object> jMap ;


    private static final Data data = new Data();
    static Data getInstance() {
        return data;
    }

    void setPersonalDataList(List<Map<String, Object>> personalList) {
        this.personalDataList = personalList;
    }

    List<Map<String, Object>> getPersonalDataList() {
        return personalDataList;
    }

    /**
     * データを読み込むメソッド
     * @param name
     * @return
     */
     String readFile(String name){
        String ret = "";
        try{
            File file = new File(name);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = bufferedReader.readLine();
            while (str!=null){
                ret += str;
                str = bufferedReader.readLine();
                bufferedReader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * データをJsonデータに変換するメソッド
     * @param name
     * @param activity
     */
    void saveFile(String name, Activity activity) {

        JSONObject jsonObject = new JSONObject();
        try{
            personalDataToJson(jsonObject);
            saveTextFile(name,jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * データをテキストファイルに書き込むメソッド
     * @param name
     * @param data
     */
    private void saveTextFile(String name, String data){
        File file = new File(name);
        File dir = new File(file.getParent());
        dir.mkdirs();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file,false));
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Jsonに変換
    private void personalDataToJson(JSONObject json) throws JSONException {
        for (int i = 0; i < personalDataList.size(); i++) {
            Map<String, Object> map = personalDataList.get(i);
            json.put("name",map.get("名前"));
            json.put("gender",map.get("性別"));
            json.put("birthYear",map.get("生年"));
            json.put("birthMonth",map.get("生月"));
            json.put("birthDay",map.get("生日"));
            json.put("age",String.valueOf(map.get("年齢")));
            json.put("address",map.get("住所"));
            //json.put("公開");
            //json.put("画像");
        }
    }

    //パースする関数
    Map<String, Object> parseJsonToMap(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        jMap.put("名前",json.get("name").toString());
        jMap.put("性別",json.get("gender").toString());
        jMap.put("生年",json.get("birthYear").toString());
        jMap.put("生月",json.get("birthMonth").toString());
        jMap.put("生日",json.get("birthDay").toString());
        jMap.put("年齢",json.get("age").toString());
        jMap.put("住所",json.get("address").toString());
        return jMap;
    }

}
