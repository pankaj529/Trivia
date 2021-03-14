package com.example.trivia.controller;

import android.content.Context;
import android.content.SharedPreferences;



public class PrefsManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String APP_ID = "App_id";


    public PrefsManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }


    public void putString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }



    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }



    public void putInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void saveHigh(int score)
    {
        int currentScore = score;
        int lastScore =sharedPreferences.getInt("App_id",0);

        if (currentScore >lastScore)
        {

            sharedPreferences.edit().putInt("App_id",currentScore).apply();
        }

    }



    public  void setState(int index) {
        sharedPreferences.edit().putInt("index_state", index).apply();
    }

    public int getState() {
        return sharedPreferences.getInt("index_state", 0);
    }





}
