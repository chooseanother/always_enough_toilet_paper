package com.example.alwaysenoughtoiletpaper.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository {
    public static final String ITEM_ADDED = "item_added_notification";
    public static final String ITEM_BOUGHT = "item_bought_notification";
    private Application app;
    private SharedPreferences sharedPreferences;
    private static SharedPreferencesRepository instance;

    private SharedPreferencesRepository(Application app) {
        this.app = app;
        sharedPreferences = app.getSharedPreferences("alwaysEnoughTP", Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesRepository getInstance(Application app){
        if (instance == null){
            instance = new SharedPreferencesRepository(app);
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public boolean getNotificationPreference(String type){
        return sharedPreferences.getBoolean(type,false);
    }

    public void setNotificationPreference(String type, boolean preference){
        sharedPreferences.edit().putBoolean(type, preference).apply();
    }
}
