package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alwaysenoughtoiletpaper.data.HouseholdRepository;
import com.example.alwaysenoughtoiletpaper.data.SharedPreferencesRepository;
import com.example.alwaysenoughtoiletpaper.data.UserInfoRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SettingsViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private UserInfoRepository userInfoRepository;
    private HouseholdRepository householdRepository;
    private final SharedPreferencesRepository sharedPreferencesRepository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        sharedPreferencesRepository = SharedPreferencesRepository.getInstance(application);
        householdRepository = HouseholdRepository.getInstance();
        userInfoRepository = UserInfoRepository.getInstance();
    }

    public void init(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
        userInfoRepository.init(userId);
    }

    public void initHouseHoldRepository(String householdId){
        if (householdId != null && !householdId.equals("")){
            householdRepository.init(householdId);
        }
    }

    public LiveData<Household> getCurrentHousehold(){
        return householdRepository.getCurrentHousehold();
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public LiveData<UserInfo> getCurrentUserInfo(){
        return userInfoRepository.getUserInfo();
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

    public void saveChanges(String householdName, String householdCode, String userName, String userPhone, String creatorId, List<HouseholdMember> members, List<ShoppingItem> shoppingList){
        //save user info
        userInfoRepository.saveUserInfo(userName, userPhone, householdCode);
        //save household info
        householdRepository.saveHousehold(new Household(householdName, creatorId, members, shoppingList));
    }
}
