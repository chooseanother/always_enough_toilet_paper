package com.example.alwaysenoughtoiletpaper.model.adapter;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position, int direction);
}
