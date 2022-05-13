package com.example.alwaysenoughtoiletpaper;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alwaysenoughtoiletpaper.databinding.ActivityJoinCreateHouseholdBinding;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinCreateHouseholdActivity extends AppCompatActivity {
    private View root;
    private Button helpButton;
    private JoinCreateHouseholdViewModel viewModel;
    private ActivityJoinCreateHouseholdBinding binding;


    private String userName;
    private String userPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("JoinCreate", "onCreate called");
        binding = ActivityJoinCreateHouseholdBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        helpButton = binding.buttonQuestionMark;
        viewModel = new ViewModelProvider(this).get(JoinCreateHouseholdViewModel.class);
        //viewModel.init();

        // check if user has a household
//        checkIfUserIsInAHousehold();

        setupHelpButton();

        // Setup create button
        setupCreateButton();

        //
        checkIfSignedIn();

        // Setup join button
        setupJoinButton();

        //
        signOutBtn();

        viewModel.getCurrentUserInfo().observe(this, userInfo -> {
            // pass info to view model to initiate household repository
            if (userInfo != null) {
                String householdId = userInfo.getHouseholdId();
                userName = userInfo.getName();
                userPhone = userInfo.getPhone();
                checkIfUserIsInAHousehold(householdId);
            }
        });

    }

    public void checkIfUserIsInAHousehold(String householdId){
        if (householdId != null){
            if(!householdId.equals("")){
                viewModel.initHouseHoldRepository(householdId);
                //Navigate to shopping list
                startActivity(new Intent(JoinCreateHouseholdActivity.this, MainActivity.class));
            }
        }
    }

    private void setupHelpButton(){
        helpButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.household_code_help_text).setTitle(R.string.household_code_help_title);
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

            viewModel.getCurrentHousehold().observe(this, household -> {
                if (household == null){
                    Toast.makeText(this, "Household does not exist", Toast.LENGTH_SHORT).show();
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

    private void signOutBtn(){
        binding.joinCreateSignOutBtn.setOnClickListener(view -> {
            viewModel.signOut();
        });
    }

    private void checkIfSignedIn(){
        LiveData<FirebaseUser> currentUser = viewModel.getCurrentUser();
        currentUser.observe(this, user -> {
            if (user != null){

            } else {
                startLoginActivity();
            }
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}