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
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        tabLayout = binding.tabLayoutShoppingList;
        viewPager = binding.shoppingListViewPager;

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new ListTabFragment(), "Shopping");
        viewPagerAdapter.addFragment(new BoughtTabFragment(), "Bought");
        viewPagerAdapter.addFragment(new HistoryTabFragment(), "History");

        viewPager.setAdapter(viewPagerAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}