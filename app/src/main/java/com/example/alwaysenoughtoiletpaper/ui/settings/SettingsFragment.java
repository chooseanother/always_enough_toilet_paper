package com.example.alwaysenoughtoiletpaper.ui.settings;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.HouseholdAndUser;
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
    private List<HistoryItem> householdHistoryItemList = new ArrayList<>();


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
        if(viewModel.getCurrentHousehold() == null){
            Navigation.findNavController(root).navigate(R.id.nav_join_create);
        }
        else {
            viewModel.getCurrentHousehold().observe(getViewLifecycleOwner(), household -> {
                if (household != null) {
                    householdName = household.getName();
                    householdCreatorId = household.getCreator();
                    if (household.getMembers() == null) {
                        members = new ArrayList<>();
                    } else {
                        members = household.getMembers();
                    }
                    if (household.getShoppinglist() == null) {
                        shoppingList = new ArrayList<>();
                    } else {
                        shoppingList = household.getShoppinglist();
                    }

                    householdHistoryItemList = household.getHistoryItemList();
                    householdHistoryItemList = householdHistoryItemList == null ? new ArrayList<>() : householdHistoryItemList;


                    //set up values in the fragment
                    householdNameET = binding.settingsHouseholdName;
                    householdNameET.setText(householdName);

                    setUpLeaveDeleteHousehold();
                }
            });
        }
    }

    private void saveButton(){
        binding.settingsSaveButton.setOnClickListener(view -> {
            HouseholdAndUser saveChanges = new HouseholdAndUser(userNameET.getText().toString(), userPhoneET.getText().toString(), householdCode, householdNameET.getText().toString(), householdCreatorId, members, shoppingList, householdHistoryItemList);
            viewModel.saveChanges(saveChanges);
            Toast.makeText(getActivity().getApplicationContext(), R.string.changes_saved, Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpLeaveDeleteHousehold(){
        //currently logged-in user is the creator
        if(viewModel.getCurrentUser().getValue().getUid().equals(householdCreatorId)){
            //display Delete household
            binding.settingsLeaveHouseholdAsAdmin.setVisibility(View.VISIBLE);
            binding.settingsLeaveHouseholdAsAdmin.setEnabled(true);
            binding.settingsLeaveHousehold.setVisibility(View.GONE);
        }
        else{
            //display Leave household
            binding.settingsLeaveHousehold.setVisibility(View.VISIBLE);
            binding.settingsLeaveHousehold.setEnabled(true);
            binding.settingsLeaveHouseholdAsAdmin.setVisibility(View.GONE);
        }
        binding.settingsLeaveHouseholdAsAdmin.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.settings_leave_admin_household_dialogue)).setTitle(R.string.settings_leave_household_as_admin);
            builder.setPositiveButton(R.string.shopping_list_yes_button, (dialogInterface, i) ->  {
                viewModel.leaveHouseholdAsAdmin(new HouseholdAndUser(viewModel.getCurrentUserId().getValue().getUid().toString(), userName, userPhone, householdCode, householdName, householdCreatorId, members, shoppingList, householdHistoryItemList));
                Navigation.findNavController(root).navigate(R.id.nav_join_create);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        binding.settingsLeaveHousehold.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.settings_leave_household_dialogue)).setTitle(R.string.settings_leave_household);
            builder.setPositiveButton(R.string.shopping_list_yes_button, (dialogInterface, i) ->  {
                viewModel.leaveHousehold(new HouseholdAndUser(viewModel.getCurrentUserId().getValue().getUid().toString(), userName, userPhone, householdCode, householdName, householdCreatorId, members, shoppingList, householdHistoryItemList));
                Navigation.findNavController(root).navigate(R.id.nav_join_create);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}