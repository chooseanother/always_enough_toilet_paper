package com.example.alwaysenoughtoiletpaper.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

public class UserInfo {
    private String name;
    private String phone;
    private String householdId;
    @Exclude
    private String uid;

    public UserInfo() {
    }

    public UserInfo(String name, String phone, String householdId) {
        this.name = name;
        this.phone = phone;
        this.householdId = householdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getUid() {
        Log.d("memberGetUid", ""+uid);
        return uid;
    }

    public void setUid(String uid) {
        Log.d("memberSetUid", uid);
        this.uid = uid;
    }
}
