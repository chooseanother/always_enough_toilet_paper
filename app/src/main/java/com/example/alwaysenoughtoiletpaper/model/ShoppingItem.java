package com.example.alwaysenoughtoiletpaper.model;

public class ShoppingItem {
    private String name;
    private boolean bought;

    public ShoppingItem(String name, boolean needed) {
        this.name = name;
        this.bought = needed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
