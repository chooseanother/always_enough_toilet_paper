package com.example.alwaysenoughtoiletpaper.ui.shopping_list.tabs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alwaysenoughtoiletpaper.model.Member;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class ListTabViewModel extends AndroidViewModel {
    private MutableLiveData<List<ShoppingItem>> shoppingListItems;

    public ListTabViewModel(@NonNull Application application) {
        super(application);
        PopulateShoppingList();
    }

    public LiveData<List<ShoppingItem>> getShoppingItems() {
        return shoppingListItems;
    }

    private void PopulateShoppingList(){
        shoppingListItems = new MutableLiveData<>();
        List<ShoppingItem> testShoppingList = new ArrayList<>();
        testShoppingList.add(new ShoppingItem("Meat", false));
        testShoppingList.add(new ShoppingItem("Eggs", false));
        testShoppingList.add(new ShoppingItem("Toilet paper", false));
        testShoppingList.add(new ShoppingItem("Ham", false));
        testShoppingList.add(new ShoppingItem("Butter", false));
        testShoppingList.add(new ShoppingItem("Milk", false));
        testShoppingList.add(new ShoppingItem("Rugbrod", false));
        testShoppingList.add(new ShoppingItem("Afkalker", false));
        testShoppingList.add(new ShoppingItem("Cucumber", false));
        testShoppingList.add(new ShoppingItem("Tomato", false));
        testShoppingList.add(new ShoppingItem("Carrot", false));
        testShoppingList.add(new ShoppingItem("Cheese", false));
        testShoppingList.add(new ShoppingItem("Flour", false));

        shoppingListItems.setValue(testShoppingList);
    }
}
