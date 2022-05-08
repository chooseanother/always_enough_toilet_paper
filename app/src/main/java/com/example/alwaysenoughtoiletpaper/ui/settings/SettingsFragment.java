package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.data.SharedPreferencesRepository;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        setCopyHouseholdCode();
        initPreferences();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initPreferences(){
        Switch itemBoughtNotification = binding.settingsItemBoughtNotification;
        Switch itemAddedNotification = binding.settingsNewItemNotification;
        itemBoughtNotification.setChecked(viewModel.getNotificationPreference(SharedPreferencesRepository.ITEM_BOUGHT));
        itemAddedNotification.setChecked(viewModel.getNotificationPreference(SharedPreferencesRepository.ITEM_ADDED));

        itemBoughtNotification.setOnClickListener(view -> {
            viewModel.setNotificationPreference(SharedPreferencesRepository.ITEM_BOUGHT, itemBoughtNotification.isChecked());
        });
        itemAddedNotification.setOnClickListener(view -> {
            viewModel.setNotificationPreference(SharedPreferencesRepository.ITEM_ADDED, itemAddedNotification.isChecked());
        });
    }

    private void setCopyHouseholdCode(){
        ClipboardManager clipboardManager = (ClipboardManager) root.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        binding.settingsCopyButton.setOnClickListener(view -> {
            ClipData clip = ClipData.newPlainText("Household code",binding.settingsHouseholdCode.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(root.getContext(), "Copied household code", Toast.LENGTH_SHORT).show();
        });
    }
}