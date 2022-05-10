package com.example.alwaysenoughtoiletpaper.ui.joincreate;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentJoinCreateHouseholdBinding;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinCreateHouseholdFragment extends Fragment {
    private View root;
    private Button helpButton;
    private JoinCreateHouseholdViewModel viewModel;
    private FragmentJoinCreateHouseholdBinding binding;


    private String userName;
    private String userPhone;

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
            if (userInfo != null) {
                String householdId = userInfo.getHouseholdId();
                userName = userInfo.getName();
                userPhone = userInfo.getPhone();
                checkIfUserIsInAHousehold(householdId);
            }
        });

        return root;
    }

    public void checkIfUserIsInAHousehold(String householdId){
        if (householdId != null){
            if(!householdId.equals("")){
                viewModel.initHouseHoldRepository(householdId);
                //Navigate to shopping list
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
            List<HistoryItem> historyItemList = new ArrayList<>();
            viewModel.createHouseHold(new Household(name,creator,members,shoppinglist, historyItemList));

            //add householdId to current user
            viewModel.addHouseholdId(new UserInfo(userName, userPhone, householdId.toString()));

            // when done navigate to shopping list
            // will happen in the observer event above checkIfUserIsInAHousehold()
        });


    }

    private void setupJoinButton(){
        // set userInfo household id to be the one joined
        binding.joinHouseholdBtn.setOnClickListener(view -> {
            String householdId = binding.joinHouseholdId.getText().toString();
            // check if household exists with that id
            viewModel.initHouseHoldRepository(householdId);

            viewModel.getCurrentHousehold().observe(getViewLifecycleOwner(), household -> {
                if (household == null){
                    Toast.makeText(getContext(), "Household does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    String currentUserId = viewModel.getCurrentUser().getValue().getUid();
                    String name = household.getName();
                    String creator = household.getCreator();
                    List<HouseholdMember> members = household.getMembers();
                    members = members == null ? new ArrayList<>() : members;

                    // make stupid check since when the user is added to the household
                    // this observer is fired again
                    boolean skip = false;
                    for (HouseholdMember member : members){
                        if (member.getUid().equals(currentUserId)){
                            skip = true;
                            break;
                        }
                    }
                    members.add(new HouseholdMember(currentUserId));
                    List<ShoppingItem> shoppinglist = household.getShoppinglist();
                    shoppinglist = shoppinglist == null ? new ArrayList<>() : shoppinglist;
                    List<HistoryItem> historyItemList = new ArrayList<>();
                    historyItemList = historyItemList == null ? new ArrayList<>() : historyItemList;

                    if (!skip) {
                        // save updated household info
                        viewModel.saveHousehold(new Household(name, creator, members, shoppinglist,historyItemList));
                    }

                    //add householdId to current user
                    viewModel.addHouseholdId(new UserInfo(userName, userPhone, householdId));

                    // when done navigate to shopping list
                    // will happen in the observer event above checkIfUserIsInAHousehold()
                }
            });

        });
    }
}