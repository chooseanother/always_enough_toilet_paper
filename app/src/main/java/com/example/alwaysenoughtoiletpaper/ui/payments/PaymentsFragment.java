package com.example.alwaysenoughtoiletpaper.ui.payments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentPaymentsBinding;

public class PaymentsFragment extends Fragment {

    private FragmentPaymentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PaymentsViewModel paymentsViewModel =
                new ViewModelProvider(this).get(PaymentsViewModel.class);

        binding = FragmentPaymentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}