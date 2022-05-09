package com.example.alwaysenoughtoiletpaper.data;

import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdLiveData;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HouseholdRepository {
    private HouseholdLiveData currentHousehold;
    private DatabaseReference dbRef;
    private static HouseholdRepository instance;

    private HouseholdRepository(){

    }

    public static synchronized HouseholdRepository getInstance(){
        if (instance == null){
            instance = new HouseholdRepository();
        }
        return instance;
    }

    public void init(String householdId){
        dbRef = FirebaseDatabase.getInstance(Database.URL).getReference().child("households").child(householdId);
        currentHousehold = new HouseholdLiveData(dbRef);
    }

    public HouseholdLiveData getCurrentHousehold() {
        return currentHousehold;
    }

    public void createHousehold(Household household){
        dbRef.setValue(household);
    }

    public void saveHousehold(Household household){
        dbRef.setValue(household);
    }
}
