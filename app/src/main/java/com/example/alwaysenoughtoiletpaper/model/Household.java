package com.example.alwaysenoughtoiletpaper.model;

import java.util.List;

public class Household {
    private String name;
    private List<Member> members;
    private List<ShoppingItem> shoppinglist;
    private String creator;

    public Household() {
    }

    public Household(String name, List<Member> members, List<ShoppingItem> shoppinglist, String creator) {
        this.name = name;
        this.members = members;
        this.shoppinglist = shoppinglist;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<ShoppingItem> getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(List<ShoppingItem> shoppinglist) {
        this.shoppinglist = shoppinglist;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
