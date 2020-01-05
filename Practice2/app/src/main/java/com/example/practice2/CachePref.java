package com.example.practice2;

import android.content.Context;
import android.content.SharedPreferences;

public class CachePref {
    private final static String RPEF_NAME = "cache";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static final String KEY_USER = "user";
    public static final String KEY_USER_LIST = "user_list";

    public CachePref() {
        Context context = ApplicationController.getInstance().getApplicationContext();
        pref = context.getSharedPreferences(RPEF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public String get(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }
    public void put(String key, String value) {
        editor.putString(key, value).commit();
    }
}
