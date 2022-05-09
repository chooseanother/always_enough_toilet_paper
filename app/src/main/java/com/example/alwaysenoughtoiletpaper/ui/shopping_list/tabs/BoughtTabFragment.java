package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingBoughtTabBinding;


public class BoughtTabFragment extends Fragment {

    private View root;
    private FragmentShoppingBoughtTabBinding binding;
//    private BoughtTabViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        viewModel = new ViewModelProvider(this).get(BoughtTabViewModel.class);
        binding = FragmentShoppingBoughtTabBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
