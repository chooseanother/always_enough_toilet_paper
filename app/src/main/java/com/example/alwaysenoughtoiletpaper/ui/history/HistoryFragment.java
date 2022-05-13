package com.example.alwaysenoughtoiletpaper.ui.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentHistoryBinding;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.adapter.HistoryAdapter;
import com.example.alwaysenoughtoiletpaper.JoinCreateHouseholdActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private View root;
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private HistoryAdapter historyAdapter;
    private RecyclerView historyList;

    // save userInfo when it is observer
    private String userName;
    private String userPhone;
    private String userHouseholdId;

    // save household info when it is observed
    private String householdCreator;
    private String householdName;
    private List<HouseholdMember> householdMemberList = new ArrayList<>();
    private List<ShoppingItem> householdShoppingItemList = new ArrayList<>();
    private List<HistoryItem> householdHistoryItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Setup recycler view
        historyList = binding.historyRV;
        historyList.hasFixedSize();
        historyList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Setup adapter
        historyAdapter = new HistoryAdapter(new ArrayList<>());
        historyList.setAdapter(historyAdapter);

        // Setup fab
        setupDeleteFab();

        initUserInfoAndHousehold();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initUserInfoAndHousehold(){
        viewModel.initUserInfoRepository();
        viewModel.getCurrentUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                userHouseholdId = userInfo.getHouseholdId();
                userName = userInfo.getName();
                userPhone = userInfo.getPhone();

                viewModel.initHouseholdRepository(userHouseholdId);

                observeHousehold();
            }
        });
    }

    private void observeHousehold() {
        if (userHouseholdId == null){

        } else if(userHouseholdId.equals("")){
            startActivity(new Intent(getContext(), JoinCreateHouseholdActivity.class));
        } else {
            viewModel.getHousehold().observe(getViewLifecycleOwner(), household -> {
                if (household != null) {
                    householdCreator = household.getCreator();
                    householdName = household.getName();
                    householdMemberList = household.getMembers();
                    householdMemberList = householdMemberList == null ? new ArrayList<>() : householdMemberList;
                    householdShoppingItemList = household.getShoppinglist();
                    householdShoppingItemList = householdShoppingItemList == null ? new ArrayList<>() : householdShoppingItemList;
                    householdHistoryItemList = household.getHistoryItemList();
                    householdHistoryItemList = householdHistoryItemList == null ? new ArrayList<>() : householdHistoryItemList;

                    // update shoppingItemAdapter::setShoppingItemList
                    historyAdapter.setHistoryItems(householdHistoryItemList);
                }
            });
        }
    }

    private void setupDeleteFab() {
        binding.historyFab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.history_delete_all_text));
            builder.setPositiveButton(R.string.history_delete_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getContext().getString(R.string.history_delete_sure));
                    builder.setPositiveButton(R.string.history_delete_yes_again, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(getContext().getString(R.string.history_delete_really_sure));
                            builder.setPositiveButton(R.string.history_delete_yes_goddamn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, new ArrayList<HistoryItem>());
                                    viewModel.saveHousehold(household);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
