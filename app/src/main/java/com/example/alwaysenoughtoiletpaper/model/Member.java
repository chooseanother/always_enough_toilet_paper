package com.example.alwaysenoughtoiletpaper.model;

public class Member {
    private String name;
    private String phoneNumber;
    private String uid;

    public Member(String name, String phoneNumber, String uid) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
