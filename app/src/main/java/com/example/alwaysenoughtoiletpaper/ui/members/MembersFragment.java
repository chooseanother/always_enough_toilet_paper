package com.example.alwaysenoughtoiletpaper.ui.members;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentMembersBinding;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.example.alwaysenoughtoiletpaper.model.UserInfoLiveData;
import com.example.alwaysenoughtoiletpaper.model.adapter.MemberAdapter;
import com.example.alwaysenoughtoiletpaper.JoinCreateHouseholdActivity;

import java.util.ArrayList;
import java.util.List;

public class MembersFragment extends Fragment {
    private MemberAdapter memberAdapter;
    private RecyclerView membersList;
    private FragmentMembersBinding binding;
    private MembersViewModel viewModel;
    private View root;

    private List<Member> membersNamesPhones;

    private String userName;
    private String userPhone;
    private String householdName;
    private String householdCode;
    private String householdCreatorId;
    private List<HouseholdMember> members;
    private List<ShoppingItem> shoppingList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(MembersViewModel.class);

        binding = FragmentMembersBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        membersNamesPhones = new ArrayList<>();

        // Setup recycler view
        setupRecyclerView();

        initUserInfo();

        //initHousehold();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupRecyclerView(){

        //TODO set up so that I get information about all the other members
        membersList = binding.membersRV;
        membersList.hasFixedSize();
        membersList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        memberAdapter = new MemberAdapter(new ArrayList<>());
        membersList.setAdapter(memberAdapter);

        // TODO is this proper handling of onclick listener, should it call a method in the view model?
        memberAdapter.setOnClickListener((member, index, delete) -> {
            if (delete){
                Toast.makeText(root.getContext(), "Delete: " + member.getName(), Toast.LENGTH_SHORT).show();
            } else {
                ClipboardManager clipboardManager = (ClipboardManager) root.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Household code",member.getPhoneNumber());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(root.getContext(), R.string.members_copied_phone_number, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initUserInfo(){
        //viewModel.init();
        viewModel.getCurrentUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                householdCode = userInfo.getHouseholdId();
                userName = userInfo.getName();
                userPhone = userInfo.getPhone();
                //viewModel.initHouseHoldRepository(householdCode);

                initHousehold();
            }
        });
    }

    private void initHousehold(){
//        if(viewModel.getCurrentHousehold() == null){
//            startActivity(new Intent(getContext(), JoinCreateHouseholdActivity.class));
//        }
        if (householdCode == null){

        } else if(householdCode.equals("")){
            startActivity(new Intent(getContext(), JoinCreateHouseholdActivity.class));
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


                    List<LiveData<UserInfo>> userLiveDataList = viewModel.initGetMembersUserInfo(members);

                    membersNamesPhones = new ArrayList<>();

                    for (HouseholdMember member : members) {
                        Log.d("memberList", member.getUid());
                    }

                    for (LiveData<UserInfo> userInfo : userLiveDataList) {
                        userInfo.observe(getViewLifecycleOwner(), userInfoLocal -> {
                            Log.d("memberObserve", userInfoLocal.getUid());
                            Member member = new Member(userInfoLocal.getName(), userInfoLocal.getPhone(), userInfoLocal.getUid());
                            if (membersNamesPhones.isEmpty()){
                                membersNamesPhones.add(member);
                            }

                            // TODO this breaks everything
                            for (Member member1:membersNamesPhones) {
                                if(!member1.getUid().equals(member.getUid())){
                                    membersNamesPhones.add(member);
                                }
                            }

                            memberAdapter.setMembers(membersNamesPhones, householdCreatorId);
                        });
                    }
                }
            });
        }
    }
}