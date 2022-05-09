package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs.HistoryTabFragment;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs.ListTabFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListTabFragment();
            case 1:
                return new HistoryTabFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Shopping";
            case 1:
                return "History";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
