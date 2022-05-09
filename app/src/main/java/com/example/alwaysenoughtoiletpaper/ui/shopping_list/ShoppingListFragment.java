package com.example.alwaysenoughtoiletpaper.ui.shopping_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentShoppingListBinding;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;
import com.example.alwaysenoughtoiletpaper.model.adapter.ShoppingItemAdapter;

public class ShoppingListFragment extends Fragment {
    private FragmentShoppingListBinding binding;
    private ShoppingListViewModel viewModel;
    private View root;
    private RecyclerView shoppingList;
    private ShoppingItemAdapter shoppingItemAdapter;
    private Button boughtButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //Set up recycler view
        shoppingList = binding.shoppingListRV;
        shoppingList.hasFixedSize();
        shoppingList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //set up adapter
        shoppingItemAdapter = new ShoppingItemAdapter(viewModel.getShoppingItems().getValue());
        shoppingItemAdapter.setOnClickListener(((item, delete) -> {
            if(delete){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getContext().getString(R.string.shopping_list_delete_item_text)+" "+item.getName()+"?").setTitle(R.string.shopping_list_delete_item_title);
                builder.setPositiveButton(R.string.shopping_list_yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteItem(item);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                viewModel.tickItem(item);
            }
        }));

        viewModel.getShoppingItems().observe(getViewLifecycleOwner(), shoppingItemAdapter::setShoppingItemList);

        shoppingList.setAdapter(shoppingItemAdapter);

        boughtButton = binding.shoppingButtonBought;
        viewModel.getNumberOfTicked().observe(getViewLifecycleOwner(), integer -> {
            if(integer == 0){
                boughtButton.setEnabled(false);
            }
            else{
                boughtButton.setEnabled(true);
            }
        });

        binding.shoppingListFab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.shopping_list_add_new_item_text);
            EditText editText = new EditText(getContext());
//            builder.setView(R.layout.dialogue_add_item);
            builder.setView(editText);
            builder.setPositiveButton(R.string.shopping_list_add_new_item_button_ok, (dialogInterface, i) -> {

//                    String newShoppingItem = dialogueBinding.dialogueEditText.getText().toString();
//                String newShoppingItem = .findViewById(R.id)getText().toString();
                String text = editText.getText().toString();
                Toast.makeText(getActivity().getApplicationContext(), "Text: "+text, Toast.LENGTH_SHORT).show();
                viewModel.addItem(new ShoppingItem(text));
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        binding.shoppingButtonBought.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.shopping_list_bought_dailogue));
            builder.setPositiveButton(R.string.shopping_list_yes_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    viewModel.bought();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

