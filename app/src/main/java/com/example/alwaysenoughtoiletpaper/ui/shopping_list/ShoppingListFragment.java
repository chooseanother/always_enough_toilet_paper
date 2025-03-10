package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.JoinCreateHouseholdActivity;
import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.adapter.ShoppingItemAdapter;
import com.example.alwaysenoughtoiletpaper.model.adapter.ItemSwipedListener;
import com.example.alwaysenoughtoiletpaper.model.adapter.SimpleItemTouchHelperCallback;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment implements ItemSwipedListener{
    private FragmentShoppingListBinding binding;
    private ShoppingListViewModel viewModel;
    private View root;
    private RecyclerView shoppingList;
    private ShoppingItemAdapter shoppingItemAdapter;
    private Button boughtButton;

    // save household info when it is observed
    private String householdCreator;
    private String householdName;
    private List<HouseholdMember> householdMemberList = new ArrayList<>();
    private List<ShoppingItem> householdShoppingItemList = new ArrayList<>();
    private List<HistoryItem> householdHistoryItemList = new ArrayList<>();

    // save userInfo when it is observer
    private String userName;
    private String userPhone;
    private String userHouseholdId;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //Set up recycler view
        setupRecyclerView();

        //set up adapter
        setupAdapter();

        boughtButton = binding.shoppingButtonBought;
        viewModel.getNumberOfTicked().observe(getViewLifecycleOwner(), integer -> {
            if(integer == 0){
                boughtButton.setEnabled(false);
            }
            else{
                boughtButton.setEnabled(true);
            }
        });

        setupFab();



        viewModel.getCurrentUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            userHouseholdId = userInfo.getHouseholdId();
            userName = userInfo.getName();
            userPhone = userInfo.getPhone();
            // BIG BROTHER!
            Log.d("testShopObserveUserInfo","household "+userHouseholdId+" user "+userName+" phone "+userPhone);
            viewModel.initHouseholdRepository(userHouseholdId);
            observeChanges();
        });

        setupBoughtButton();

        // Setup swipe gesture
        setupSwipeGesture();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.clearTicked();
    }

    private void setupRecyclerView() {
        shoppingList = binding.shoppingListRV;
        shoppingList.hasFixedSize();
        shoppingList.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    private void setupAdapter() {
        shoppingItemAdapter = new ShoppingItemAdapter(new ArrayList<>(),this);
        shoppingItemAdapter.setOnClickListener(((item, delete, index, isChecked) -> {
            if (delete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getContext().getString(R.string.shopping_list_delete_item_text) + " " + item.getName() + "?").setTitle(R.string.shopping_list_delete_item_title);
                builder.setPositiveButton(R.string.shopping_list_yes_button, (dialogInterface, i) -> {

                    householdShoppingItemList.remove(item);
                    Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
                    viewModel.updateHousehold(household);
                    viewModel.clearTicked();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                viewModel.tickItem(item);
            }
        }));

        shoppingList.setAdapter(shoppingItemAdapter);
    }

    private void setupFab(){
        binding.shoppingListFab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.shopping_list_add_new_item_text);
            EditText editText = new EditText(getContext());
            builder.setView(editText);
            builder.setPositiveButton(R.string.shopping_list_add_new_item_button_ok, (dialogInterface, i) -> {
                String text = editText.getText().toString();
                ShoppingItem shoppingItem = new ShoppingItem(text);
                householdShoppingItemList.add(shoppingItem);
                Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
                viewModel.updateHousehold(household);
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void setupBoughtButton(){
        binding.shoppingButtonBought.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.shopping_list_bought_dailogue));
            builder.setPositiveButton(R.string.shopping_list_yes_button, (dialogInterface, item) -> {
                List<ShoppingItem> tickItems = viewModel.getTickedItems();
                for (ShoppingItem shoppingItem : tickItems) {
                    householdHistoryItemList.add(new HistoryItem(userName, shoppingItem.getName()));
                    householdShoppingItemList.remove(shoppingItem);
                }
                Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
                viewModel.updateHousehold(household);
                viewModel.clearTicked();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void setupSwipeGesture() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(shoppingItemAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(shoppingList);
    }

    private void observeChanges() {
        if (userHouseholdId == null){

        } else if(userHouseholdId.equals("")){
            startActivity(new Intent(getContext(), JoinCreateHouseholdActivity.class));
        } else {
            LiveData<Household> householdLiveData = viewModel.getHousehold();
            Log.d("testShop","householdlivedata "+householdLiveData);
            if (householdLiveData != null) {
                householdLiveData.observe(getViewLifecycleOwner(), household -> {
                    Log.d("testShopObserveHouse", ""+household);
                    if (household != null) {
                        householdCreator = household.getCreator();
                        householdName = household.getName();
                        householdMemberList = household.getMembers();
                        householdMemberList = householdMemberList == null ? new ArrayList<>() : householdMemberList;
                        householdShoppingItemList = household.getShoppinglist();
                        householdShoppingItemList = householdShoppingItemList == null ? new ArrayList<>() : householdShoppingItemList;
                        householdHistoryItemList = household.getHistoryItemList();
                        householdHistoryItemList = householdHistoryItemList == null ? new ArrayList<>() : householdHistoryItemList;

                        // update shoppingItemAdapter
                        shoppingItemAdapter.setShoppingItemList(householdShoppingItemList);
                    }
                });
            }
        }
    }

    @Override
    public void itemSwipedRight(String item) {
        Toast.makeText(getContext(), "Bought: " + item, Toast.LENGTH_SHORT).show();
        householdHistoryItemList.add(new HistoryItem(userName, item));
        Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
        viewModel.updateHousehold(household);
    }

    @Override
    public void itemSwipedLeft(String item) {
        Toast.makeText(getContext(), "Removed: " + item, Toast.LENGTH_SHORT).show();
        Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
        viewModel.updateHousehold(household);
    }

    @Override
    public void itemMoved(){
        Household household = new Household(householdName, householdCreator, householdMemberList, householdShoppingItemList, householdHistoryItemList);
        viewModel.updateHousehold(household);
    }
}

