package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentBoughtTabBinding;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentListTabBinding;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.ShoppingListViewModel;

public class ListTabFragment extends Fragment {

    private View root;
    private FragmentListTabBinding binding;
//    private ListTabViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        viewModel = new ViewModelProvider(this).get(ListTabViewModel.class);
        binding = FragmentListTabBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
