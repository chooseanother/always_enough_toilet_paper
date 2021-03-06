package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.data.HouseholdRepository;
import com.example.alwaysenoughtoiletpaper.data.UserInfoRepository;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final HouseholdRepository householdRepository;

    private Application application;
    private MutableLiveData<Integer> numberOfTicked;
    private ArrayList<ShoppingItem> tickedItems;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        userInfoRepository = UserInfoRepository.getInstance();
        householdRepository = HouseholdRepository.getInstance();
        this.application = application;
        numberOfTicked = new MutableLiveData<>();
        numberOfTicked.setValue(0);
        tickedItems = new ArrayList<>();
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

    public void initHouseholdRepository(String householdId){
        householdRepository.init(householdId);
    }

    public void updateHousehold(Household household){
        householdRepository.saveHousehold(household);
    }

    public void tickItem(ShoppingItem item){
        //ticked in
        if(!tickedItems.contains(item)){
            tickedItems.add(item);
            numberOfTicked.setValue(tickedItems.size());
        }
        //ticked out
        else {
            tickedItems.remove(item);
            numberOfTicked.setValue(tickedItems.size());
        }
    }

    public void clearTicked(){
        tickedItems.clear();
        numberOfTicked.setValue(tickedItems.size());
    }

    public MutableLiveData<Integer> getNumberOfTicked(){
        return numberOfTicked;
    }

    public ArrayList<ShoppingItem> getTickedItems() {
        return tickedItems;
    }
}