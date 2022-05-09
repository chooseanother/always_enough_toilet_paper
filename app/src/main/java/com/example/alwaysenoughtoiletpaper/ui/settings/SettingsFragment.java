package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.data.SharedPreferencesRepository;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentSettingsBinding;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private View root;

    private EditText householdNameET;
    private EditText userNameET;
    private EditText userPhoneET;

    private String userName;
    private String userPhone;
    private String householdName;
    private String householdCode;
    private String householdCreatorId;
    private List<HouseholdMember> members;
    private List<ShoppingItem> shoppingList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        setCopyHouseholdCode();
        initPreferences();

        initUserInfo();
        initHousehold();
        saveButton();
        setUpLeaveDeleteHousehold();

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

    private void initUserInfo(){
        viewModel.init();
        viewModel.getCurrentUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                householdCode = userInfo.getHouseholdId();
                userName = userInfo.getName();
                userPhone = userInfo.getPhone();

                viewModel.initHouseHoldRepository(householdCode);

                //set up values in the fragment
                TextView householdCodeTV = binding.settingsHouseholdCode;
                householdCodeTV.setText(householdCode);
                userNameET = binding.settingsName;
                userNameET.setText(userName);
                userPhoneET = binding.settingsPhone;
                userPhoneET.setText(userPhone);
            }
        });

    }

    private void initHousehold(){
        viewModel.getCurrentHousehold().observe(getViewLifecycleOwner(), household -> {
            if (household != null) {
                householdName = household.getName();
                householdCreatorId = household.getCreator();
                if(household.getMembers() == null){
                    members = new ArrayList<>();
                }
                else{
                    members = household.getMembers();
                }
                if(household.getShoppinglist() == null){
                    shoppingList = new ArrayList<>();
                }
                else {
                    shoppingList = household.getShoppinglist();
                }
                //set up values in the fragment
                householdNameET = binding.settingsHouseholdName;
                householdNameET.setText(householdName);
            }
        });
    }

    private void saveButton(){
        binding.settingsSaveButton.setOnClickListener(view -> {
            viewModel.saveChanges(householdNameET.getText().toString(), householdCode, userNameET.getText().toString(), userPhoneET.getText().toString(), householdCreatorId, members, shoppingList);
            Toast.makeText(getActivity().getApplicationContext(), R.string.changes_saved, Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpLeaveDeleteHousehold(){
        //currently logged-in user is the creator
        if(viewModel.getCurrentUser().getValue().getUid().equals(householdCreatorId)){
            //display Delete household
            binding.settingsDeleteHousehold.setVisibility(View.VISIBLE);
            binding.settingsDeleteHousehold.setEnabled(true);
            binding.settingsLeaveHousehold.setVisibility(View.GONE);
        }
        else{
            //display Leave household
            binding.settingsLeaveHousehold.setVisibility(View.VISIBLE);
            binding.settingsLeaveHousehold.setEnabled(true);
            binding.settingsDeleteHousehold.setVisibility(View.GONE);
        }
    }
}