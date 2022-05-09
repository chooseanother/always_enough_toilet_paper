package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListTabBinding;
import com.example.alwaysenoughtoiletpaper.model.adapter.ShoppingItemAdapter;

public class ListTabFragment extends Fragment {

    private View root;
    private FragmentShoppingListTabBinding binding;
    private ListTabViewModel viewModel;
    private RecyclerView shoppingList;
    private ShoppingItemAdapter shoppingItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ListTabViewModel.class);
        binding = FragmentShoppingListTabBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //Set up recycler view
        shoppingList = binding.shoppingListRV;
        shoppingList.hasFixedSize();
        shoppingList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //set up adapter
        shoppingItemAdapter = new ShoppingItemAdapter(viewModel.getShoppingItems().getValue());
        shoppingItemAdapter.setOnClickListener(((item, delete) -> {
            if(delete){
                Toast.makeText(root.getContext(), "Delete: " + item.getName(), Toast.LENGTH_SHORT).show();

            }
            else{
                item.setBought(true);
            }
        }));

        viewModel.getShoppingItems().observe(getViewLifecycleOwner(), shoppingItemAdapter::setShoppingItemList);

        shoppingList.setAdapter(shoppingItemAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
