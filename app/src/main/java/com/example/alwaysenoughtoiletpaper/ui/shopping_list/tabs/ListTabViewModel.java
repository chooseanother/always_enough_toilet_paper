package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class ListTabViewModel extends AndroidViewModel {
    private MutableLiveData<List<ShoppingItem>> shoppingListItems;
    List<ShoppingItem> testShoppingList;
    private Application application;
    MutableLiveData<Integer> numberOfTicked;
    ArrayList<ShoppingItem> tickedItems;


    public ListTabViewModel(@NonNull Application application) {

        super(application);
        this.application = application;
        PopulateShoppingList();
        numberOfTicked = new MutableLiveData<>();
        numberOfTicked.setValue(0);
        tickedItems = new ArrayList<>();
    }

    public MutableLiveData<List<ShoppingItem>> getShoppingItems() {
        return shoppingListItems;
    }

    private void PopulateShoppingList(){
        shoppingListItems = new MutableLiveData<>();
        testShoppingList = new ArrayList<>();
        testShoppingList.add(new ShoppingItem("Meat"));
        testShoppingList.add(new ShoppingItem("Eggs"));
        testShoppingList.add(new ShoppingItem("Toilet paper"));
        testShoppingList.add(new ShoppingItem("Ham"));
        testShoppingList.add(new ShoppingItem("Butter"));
        testShoppingList.add(new ShoppingItem("Milk"));
        testShoppingList.add(new ShoppingItem("Rugbrod"));
        testShoppingList.add(new ShoppingItem("Afkalker"));
        testShoppingList.add(new ShoppingItem("Cucumber"));
        testShoppingList.add(new ShoppingItem("Tomato"));
        testShoppingList.add(new ShoppingItem("Carrot"));
        testShoppingList.add(new ShoppingItem("Cheese"));
        testShoppingList.add(new ShoppingItem("Flour"));

        shoppingListItems.setValue(testShoppingList);
    }

    public void tickItem(ShoppingItem item){
        //ticked in
        if(!tickedItems.contains(item)){
            tickedItems.add(item);
            numberOfTicked.setValue(tickedItems.size());
        }
        //ticked out
        else {
            tickedItems.remove(item);
            numberOfTicked.setValue(tickedItems.size());
        }
    }

    public void deleteItem(ShoppingItem item){
        //if ticked, remove from ticked list
        if(tickedItems.contains(item)){
            tickedItems.remove(item);
            numberOfTicked.setValue(tickedItems.size());
        }
        testShoppingList.remove(item);
        shoppingListItems.setValue(testShoppingList);
    }

    public MutableLiveData<Integer> getNumberOfTicked(){
        return numberOfTicked;
    }

    public void addItem(ShoppingItem newItem){
        boolean contains = false;
        for (int i = 0; i < testShoppingList.size(); i++) {
            if(testShoppingList.get(i).getName().equals(newItem.getName())){
                contains = true;
            }
        }
        if(contains){
            Toast.makeText(application, R.string.shopping_list_add_new_item_exists, Toast.LENGTH_LONG).show();
        }
        else{
            testShoppingList.add(newItem);
            shoppingListItems.setValue(testShoppingList);
        }
    }

    public void bought(){
        //set the things to history
        for (int i = 0; i < tickedItems.size(); i++) {
            deleteItem(tickedItems.get(i));
        }
    }
}
