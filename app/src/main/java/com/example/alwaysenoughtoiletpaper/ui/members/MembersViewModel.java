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
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
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


    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public LiveData<UserInfo> getCurrentUserInfo(){
        return userInfoRepository.getUserInfo();
    }

    public LiveData<Household> getCurrentHousehold(){
        return householdRepository.getCurrentHousehold();
    }

    /*private void PopulateMembers(){
        List<Member> testMembers = new ArrayList<>();
        testMembers.add(new Member("Kim Tranberg long long long long long long name","+4512345678"));
        testMembers.add(new Member("Markéta Lapčíková","+4587654321"));
        testMembers.add(new Member("Mongo Bongo","+4512341234"));
        testMembers.add(new Member("Steen Steensen Blicher","+4556785678"));
        testMembers.add(new Member("Allan Henriksen","+4587878787"));
        testMembers.add(new Member("Test Tester Testsen","+45918273645"));
        testMembers.add(new Member("Lorem Ipsum","+4545362718"));
        testMembers.add(new Member("Saepe Aut Dolor","+4543215678"));
        testMembers.add(new Member("Aut Repudiandae Velit","+4512211221"));
        testMembers.add(new Member("Molestias Ullam","+4534434334"));
        testMembers.add(new Member("Deleniti Facere","+4556655656"));

        members.setValue(testMembers);
    }*/


}