package com.example.alwaysenoughtoiletpaper.ui.joincreate;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alwaysenoughtoiletpaper.data.HouseholdRepository;
import com.example.alwaysenoughtoiletpaper.data.UserInfoRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class JoinCreateHouseholdViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;
    private HouseholdRepository householdRepository;

    public JoinCreateHouseholdViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
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

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public LiveData<UserInfo> getCurrentUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public LiveData<Household> getCurrentHousehold(){
        return householdRepository.getCurrentHousehold();
    }

    public Boolean isUserInAHousehold(){
        String householdId = userInfoRepository.getHouseholdId();
        return householdId == null ? false : householdId.equals("") ? false : true;
//        return householdId != null && (!householdId.equals(""));
    }

    public void saveHousehold(Household household){
        createHouseHold(household);
    }

    public void createHouseHold(Household household){
        householdRepository.createHousehold(household);
    }

    public void addHouseholdId(UserInfo userInfo){
        userInfoRepository.addHouseholdId(userInfo);
    }
}
