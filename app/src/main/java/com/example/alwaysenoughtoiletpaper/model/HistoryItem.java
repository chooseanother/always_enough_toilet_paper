package com.example.alwaysenoughtoiletpaper.model;

public class HistoryItem {
    private String name;
    private String item;

    public HistoryItem() {
    }

    public HistoryItem(String name, String item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
