package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingHistoryTabBinding;

public class HistoryTabFragment extends Fragment {

    private View root;
    private FragmentShoppingHistoryTabBinding binding;
//    private HistoryTabViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        viewModel = new ViewModelProvider(this).get(HistoryTabViewModel.class);
        binding = FragmentShoppingHistoryTabBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
