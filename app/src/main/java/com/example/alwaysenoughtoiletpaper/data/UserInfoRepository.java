package com.example.alwaysenoughtoiletpaper.data;

import android.util.Log;

import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.example.alwaysenoughtoiletpaper.model.UserInfoLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoRepository {
    private static UserInfoRepository instance;
    private DatabaseReference dbRef;
    private UserInfoLiveData userInfo;

    private UserInfoRepository() {
    }

    public static synchronized UserInfoRepository getInstance(){
        if (instance == null)
            instance = new UserInfoRepository();
        return instance;
    }

    public void init(String userId){
        dbRef = FirebaseDatabase.getInstance(Database.URL).getReference().child("users").child(userId);
        userInfo = new UserInfoLiveData(dbRef);
    }

    public void saveUserInfo(String name, String phone, String householdId){
        dbRef.setValue(new UserInfo(name,phone,householdId));
    }

    public UserInfoLiveData getUserInfo() {
        return userInfo;
    }

    public String getHouseholdId(){
        return userInfo.getValue().getHouseholdId();
    }

    public void addHouseholdId(UserInfo userInfo){
        dbRef.setValue(userInfo);
    }
}
