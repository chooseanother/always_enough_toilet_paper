package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentSettingsBinding;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentSlideshowBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ClipboardManager clipboardManager = (ClipboardManager) root.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        binding.settingsCopyButton.setOnClickListener(view -> {
            ClipData clip = ClipData.newPlainText("Household code",binding.settingsHouseholdCode.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(root.getContext(), "Copied household code", Toast.LENGTH_SHORT).show();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}