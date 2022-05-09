package com.example.alwaysenoughtoiletpaper.ui.joincreate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentJoinCreateHouseholdBinding;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.ShoppingListViewModel;

import java.util.UUID;

public class JoinCreateHouseholdFragment extends Fragment {
    private View root;
    private Button helpButton;
    private JoinCreateHouseholdViewModel viewModel;
    private FragmentJoinCreateHouseholdBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinCreateHouseholdBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        helpButton = binding.buttonQuestionMark;
        viewModel = new ViewModelProvider(this).get(JoinCreateHouseholdViewModel.class);
        viewModel.init();

        // check if user has a household
//        checkIfUserIsInAHousehold();

        setupHelpButton();

        // Setup create button
        setupCreateButton();

        // Setup join button
        setupJoinButton();

        viewModel.getCurrentUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            // pass info to view model to initiate household repository
            String householdId = userInfo.getHouseholdId();
            checkIfUserIsInAHousehold(householdId);
//            viewModel.initHouseHoldRepository(householdId);
//            viewModel.getCurrentHousehold().observe(getViewLifecycleOwner(), household -> {
//
//            });
//            checkIfUserIsInAHousehold();
        });

        return root;
    }

    public void checkIfUserIsInAHousehold(String householdId){
        if (householdId != null){
            if(!householdId.equals("")){
//                viewModel.initHouseHoldRepository(householdId);
                //Navigate to shopping list
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupHelpButton(){
        helpButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.household_code_help_text)).setTitle(R.string.household_code_help_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void setupCreateButton(){
        // create random household id
        UUID householdId = UUID.randomUUID();
        // init household repository with that id
        viewModel.initHouseHoldRepository(householdId.toString());
        // save household info there

    }

    private void setupJoinButton(){
        // check if household exists with that id
        // set userInfo household id to be the one joined

    }
}