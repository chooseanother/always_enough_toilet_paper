package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alwaysenoughtoiletpaper.data.SharedPreferencesRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class SettingsViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final SharedPreferencesRepository sharedPreferencesRepository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        sharedPreferencesRepository = SharedPreferencesRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public SharedPreferences getPreferences(){
        return sharedPreferencesRepository.getSharedPreferences();
    }

    public boolean getNotificationPreference(String type){
        return sharedPreferencesRepository.getNotificationPreference(type);
    }

    public void setNotificationPreference(String type, boolean preference){
        sharedPreferencesRepository.setNotificationPreference("item_added_notification", preference);
    }
}
