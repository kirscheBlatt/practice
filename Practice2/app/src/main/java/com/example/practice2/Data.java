package com.example.practice2;

import android.app.Activity;
import android.icu.text.StringSearch;

import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * データの入出力とパース
 * 基本はリストで管理、必要に応じて切り出しや変換を行う
 */

class Data {


    /**
     * 登録されたデータを格納するリスト
     */
    List<Map<String, Object>> personalDataList = new ArrayList<>();
    Map<String , Object> jMap = new HashMap<>();

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
     */
    void saveFile(String name) {
        try{
            saveTextFile(name,makeJsonArrey((ArrayList)personalDataList).toString());
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

    //個人情報をjsonに変換
    private JSONObject personalDataToJson(int po) throws JSONException {
            Map<String, Object> map = personalDataList.get(po);
            JSONObject json = new JSONObject();
            json.put("name",map.get("名前"));
            json.put("gender",map.get("性別"));
            json.put("birthYear",map.get("生年"));
            json.put("birthMonth",map.get("生月"));
            json.put("birthDay",map.get("生日"));
            json.put("age",String.valueOf(map.get("年齢")));
            json.put("address",map.get("住所"));
            json.put("open",map.get("公開"));
            json.put("image",map.get("画像"));
            return json;
    }

    //パースする関数
    private Map<String, Object> parseJsonToMap(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        jMap.put("名前",json.get("name").toString());
        jMap.put("性別",json.get("gender").toString());
        jMap.put("生年",json.get("birthYear").toString());
        jMap.put("生月",json.get("birthMonth").toString());
        jMap.put("生日",json.get("birthDay").toString());
        jMap.put("年齢",json.get("age").toString());
        jMap.put("住所",json.get("address").toString());
//      jMap.put("公開",json.get(" open"));
//      jMap.put("画像",json.get("image").toString());
        return jMap;
    }

    //JsonArrayを作る関数
    JSONArray makeJsonArrey(ArrayList<Map<String, Object>> list) throws JSONException {

        JSONArray jsonArray = new JSONArray();
        if (list != null) {
            for (int i = 0; i<list.size();i++){
                jsonArray.put(personalDataToJson(i));
            }
        }
        return jsonArray;
    }

    //JsonArrayをパースする関数
    void parseJsonArray(String string) throws JSONException {
        ArrayList<Map<String ,Object>> listdata = new ArrayList<Map<String,Object>>();
        JSONArray array = new JSONArray(string);

            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                Map<String, Object> map = (Map<String, Object>) parseJsonToMap(jsonObject.toString());
                listdata.add(i,map)
                ;
            }

        personalDataList=listdata;
    }
}
