package com.nhn.ditimtrieuphu.common;


import android.content.Context;
import android.content.SharedPreferences;

import com.nhn.ditimtrieuphu.App;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
        SharedPreferences pref= App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return pref.contains(key);
    }

    public void savePref(String key, String value) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        pref.edit().putString(key,value).apply();
    }

    public void saveScore(String key, String value) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        pref.edit().putString(key,value).apply();
    }

    public String getPref(String key) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return pref.getString(key,null);
    }

    public String getScore(String key) {
        SharedPreferences pref=App.getInstance().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return pref.getString(key,null);
    }

    public void clearPref(String key) {
        SharedPreferences pref = App.getInstance()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }

    public double convertStringScoreToDouble(String score) {
        String txt =  score.replace(".", "");
        return Double.parseDouble(txt);
    }

    public double convertScoreUserToDouble(String score) {
        double soDouble = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        try {
            Number number = decimalFormat.parse(score);
            soDouble = number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return soDouble;
    }

    /**
     * Lay gia tri cua object voi field tuong ung
     * @param property
     * @param object
     * @return
     * @throws NoSuchMethodException
     */
    public Object getPropertyValue(String property, Object object) throws NoSuchMethodException {
        try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);

            return field.get(object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Convert object thanh map de dung cho firebase
     * @param keys
     * @param object
     * @return
     */
    public Map<String, Object> convertToDto(String[] keys, Object object) {
        Map<String, Object> map = new HashMap<>();
        for (String key: keys) {
            try {
                map.put(key, getPropertyValue(key, object));
            } catch (NoSuchMethodException e) {
                //TODO
            }
        }
        return map;
    }
}