package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentBoughtTabBinding;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.ShoppingListViewModel;

public class BoughtTabFragment extends Fragment {

    private View root;
    private FragmentBoughtTabBinding binding;
//    private BoughtTabViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        viewModel = new ViewModelProvider(this).get(BoughtTabViewModel.class);
        binding = FragmentBoughtTabBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
