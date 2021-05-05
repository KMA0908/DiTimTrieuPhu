package com.example.ditimtrieuphu;


import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtils {
    private static final String FILE_NAME = "file_shared";
    private static CommonUtils instance;
    private CommonUtils() {
        //for singleton
    }

    public static CommonUtils getInstance(){
        if(instance==null){
            instance = new CommonUtils();
        }
        return instance;
    }

    public boolean isExistPref(String key) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return pref.contains(key);
    }

    public void savePref(String key, String value) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        pref.edit().putString(key,value).apply();
    }

    public String getPref(String key) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return pref.getString(key,null);
    }

    public void clearPref(String key) {
        SharedPreferences pref = App.getInstance()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }
}