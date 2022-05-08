package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs.BoughtTabFragment;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs.HistoryTabFragment;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs.ListTabFragment;
import com.google.android.material.tabs.TabLayout;

public class ShoppingListFragment extends Fragment {
    private FragmentShoppingListBinding binding;
    private ShoppingListViewModel viewModel;
    private View root;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabsPagerAdapter tabsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        tabsPagerAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager(), 3);
        viewPager = binding.shoppingListViewPager;
        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout = binding.tabLayoutShoppingList;
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}