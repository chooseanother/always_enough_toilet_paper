package com.example.alwaysenoughtoiletpaper.model;

import java.util.List;

public class HouseholdAndUser {
    private String userId;
    private String userName;
    private String phone;
    private String householdId;
    private String householdName;
    private String creator;
    private List<HouseholdMember> members;
    private List<ShoppingItem> shoppinglist;
    private List<HistoryItem> historyItemList;


    public HouseholdAndUser(String userId, String userName, String phone, String householdId, String householdName, String creator, List<HouseholdMember> members, List<ShoppingItem> shoppinglist, List<HistoryItem> historyItemList) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.householdId = householdId;
        this.householdName = householdName;
        this.creator = creator;
        this.members = members;
        this.shoppinglist = shoppinglist;
        this.historyItemList = historyItemList;
    }

    public HouseholdAndUser(String userName, String phone, String householdId, String householdName, String creator, List<HouseholdMember> members, List<ShoppingItem> shoppinglist, List<HistoryItem> historyItemList) {
        this.userName = userName;
        this.phone = phone;
        this.householdId = householdId;
        this.householdName = householdName;
        this.creator = creator;
        this.members = members;
        this.shoppinglist = shoppinglist;
        this.historyItemList = historyItemList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<HouseholdMember> getMembers() {
        return members;
    }

    public void setMembers(List<HouseholdMember> members) {
        this.members = members;
    }

    public List<ShoppingItem> getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(List<ShoppingItem> shoppinglist) {
        this.shoppinglist = shoppinglist;
    }

    public List<HistoryItem> getHistoryItemList() {
        return historyItemList;
    }

    public void setHistoryItemList(List<HistoryItem> historyItemList) {
        this.historyItemList = historyItemList;
    }
}
