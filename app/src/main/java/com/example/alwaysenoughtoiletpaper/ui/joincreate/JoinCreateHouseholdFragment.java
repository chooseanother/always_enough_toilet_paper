package com.example.alwaysenoughtoiletpaper.ui.joincreate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentJoinCreateHouseholdBinding;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.example.alwaysenoughtoiletpaper.ui.shopping_list.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinCreateHouseholdFragment extends Fragment {
    private View root;
    private Button helpButton;
    private JoinCreateHouseholdViewModel viewModel;
    private FragmentJoinCreateHouseholdBinding binding;


    private String user_name;
    private String user_phone;

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
            user_name = userInfo.getName();
            user_phone = userInfo.getPhone();
            Log.d("testing",""+user_name+user_phone);
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
        Log.d("testing",householdId);
        if (householdId != null){
            Log.d("testing", householdId);
            if(!householdId.equals("")){
                viewModel.initHouseHoldRepository(householdId);
                //Navigate to shopping list
                Log.d("testing", "before navigation");
                Navigation.findNavController(root).navigate(R.id.nav_shopping_list);
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
        binding.createHouseholdBtn.setOnClickListener(view -> {
            // create random household id
            UUID householdId = UUID.randomUUID();
            // init household repository with that id
            viewModel.initHouseHoldRepository(householdId.toString());
            // save household info there
            String name = binding.createHouseholdName.getText().toString();
            name = name.equals("") ? "New Household" : name;
            String creator = viewModel.getCurrentUser().getValue().getUid();
            List<HouseholdMember> members = new ArrayList<>();
            members.add(new HouseholdMember(creator));

            List<ShoppingItem> shoppinglist = new ArrayList<>();
            viewModel.createHouseHold(new Household(name,creator,members,shoppinglist));

            //add householdId to current user
            viewModel.addHouseholdId(new UserInfo(user_name, user_phone, householdId.toString()));

            // when done navigate to shopping list
            // will happen in the observer event above checkIfUserIsInAHousehold()
        });


    }

    private void setupJoinButton(){
        // check if household exists with that id
        // set userInfo household id to be the one joined

    }
}