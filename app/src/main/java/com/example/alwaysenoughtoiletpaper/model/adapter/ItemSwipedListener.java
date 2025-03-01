package com.example.alwaysenoughtoiletpaper.model.adapter;

public interface ItemSwipedListener {
    void itemSwipedRight(String item);
    void itemSwipedLeft(String item);

    void itemMoved();
}
