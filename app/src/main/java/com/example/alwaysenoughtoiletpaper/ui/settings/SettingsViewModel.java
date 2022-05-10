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
import com.example.alwaysenoughtoiletpaper.model.HouseholdAndUser;
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

    public void saveChanges(HouseholdAndUser householdAndUser){
        //save user info
        userInfoRepository.saveUserInfo(householdAndUser.getUserName(), householdAndUser.getPhone(), householdAndUser.getHouseholdId());
        //save household info
        householdRepository.saveHousehold(new Household(householdAndUser.getHouseholdName(), householdAndUser.getCreator(), householdAndUser.getMembers(), householdAndUser.getShoppinglist(), householdAndUser.getHistoryItemList()));
    }

    //currently logged in user Uid
    public LiveData<FirebaseUser> getCurrentUserId(){
        return userRepository.getCurrentUser();
    }

    public void leaveHousehold(HouseholdAndUser householdAndUser){
        //remove this user id from his household's members
        List<HouseholdMember> newMembers = householdAndUser.getMembers();
        //find this member in the member list of household
        for (HouseholdMember member:newMembers) {
            if(member.getUid().equals(householdAndUser.getUserId())){
                //remove him
                newMembers.remove(member);
                break;
            }
        }
        //save changes in database
        householdRepository.saveHousehold(new Household(householdAndUser.getHouseholdName(), householdAndUser.getCreator(), newMembers, householdAndUser.getShoppinglist(), householdAndUser.getHistoryItemList()));

        //remove the household from the member
        userInfoRepository.saveUserInfo(householdAndUser.getUserName(), householdAndUser.getPhone(), "");
    }

    public void leaveHouseholdAsAdmin(HouseholdAndUser householdAndUser){
        //check if there is anybody else in the household
        if(householdAndUser.getMembers().size() == 1){
            //this user is the only one -> delete household
            householdRepository.saveHousehold(new Household());
        }
        //more people in household
        else {
            //remove this user id from his household's members
            List<HouseholdMember> newMembers = householdAndUser.getMembers();
            //find this member in the member list of household
            for (HouseholdMember member : newMembers) {
                if (member.getUid().equals(householdAndUser.getUserId())) {
                    //remove him
                    newMembers.remove(member);
                    break;
                }
            }
            //set the creator of household to a random person
            String newCreator = newMembers.get(0).getUid();
            //save changes in database
            householdRepository.saveHousehold(new Household(householdAndUser.getHouseholdName(), newCreator, newMembers, householdAndUser.getShoppinglist(), householdAndUser.getHistoryItemList()));
        }

        //remove the household from the member
        userInfoRepository.saveUserInfo(householdAndUser.getUserName(), householdAndUser.getPhone(), "");
    }
}
