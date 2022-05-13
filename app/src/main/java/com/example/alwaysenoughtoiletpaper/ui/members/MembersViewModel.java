package com.example.alwaysenoughtoiletpaper.ui.members;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwaysenoughtoiletpaper.data.HouseholdRepository;
import com.example.alwaysenoughtoiletpaper.data.UserInfoRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.example.alwaysenoughtoiletpaper.model.UserInfoLiveData;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MembersViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    //private MutableLiveData<List<Member>> members;
    private HouseholdRepository householdRepository;
    private UserInfoRepository userInfoRepository;

    public MembersViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        householdRepository = HouseholdRepository.getInstance();
        userInfoRepository = UserInfoRepository.getInstance();
        //members = new MutableLiveData<>();
        //PopulateMembers();
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

    public List<LiveData<UserInfo>> initGetMembersUserInfo(List<HouseholdMember> members){
        return userInfoRepository.initHouseholdMembers(members);
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
}