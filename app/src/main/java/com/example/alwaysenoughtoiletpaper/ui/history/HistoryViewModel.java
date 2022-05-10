package com.example.alwaysenoughtoiletpaper.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwaysenoughtoiletpaper.data.HouseholdRepository;
import com.example.alwaysenoughtoiletpaper.data.UserInfoRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;
    private HouseholdRepository householdRepository;

    public HistoryViewModel(Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        householdRepository = HouseholdRepository.getInstance();
    }

    public void initUserInfoRepository(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
        userInfoRepository.init(userId);
    }

    public void initHouseholdRepository(String householdId){
        householdRepository.init(householdId);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public LiveData<UserInfo> getCurrentUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public LiveData<Household> getHousehold(){
        return householdRepository.getCurrentHousehold();
    }

    public void saveHousehold(Household household){
        householdRepository.saveHousehold(household);
    }
}
