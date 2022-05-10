package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import android.app.AlertDialog;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Household;
import com.example.alwaysenoughtoiletpaper.model.HouseholdMember;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.adapter.ShoppingItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {
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
        viewModel.initUserInfoRepository();

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
            userName = userInfo.getName();
            userPhone = userInfo.getPhone();
        });

        setupBoughtButton();

        // BIG BROTHER!
        observeChanges();

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
        shoppingItemAdapter = new ShoppingItemAdapter(new ArrayList<>());
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
//                Toast.makeText(getActivity().getApplicationContext(), "Text: "+text, Toast.LENGTH_SHORT).show();
//                viewModel.addItem(new ShoppingItem(text));
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

    private void observeChanges() {
        if(viewModel.getHousehold() == null){
            Navigation.findNavController(root).navigate(R.id.nav_join_create);
        } else {
            viewModel.getHousehold().observe(getViewLifecycleOwner(), household -> {
                householdCreator = household.getCreator();
                householdName = household.getName();
                householdMemberList = household.getMembers();
                householdMemberList = householdMemberList == null ? new ArrayList<>() : householdMemberList;
                householdShoppingItemList = household.getShoppinglist();
                householdShoppingItemList = householdShoppingItemList == null ? new ArrayList<>() : householdShoppingItemList;
                householdHistoryItemList = household.getHistoryItemList();
                householdHistoryItemList = householdHistoryItemList == null ? new ArrayList<>() : householdHistoryItemList;


                // update shoppingItemAdapter::setShoppingItemList
                shoppingItemAdapter.setShoppingItemList(householdShoppingItemList);

                if (householdShoppingItemList == null) {
                    boughtButton.setVisibility(View.INVISIBLE);
                } else {
                    boughtButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}

