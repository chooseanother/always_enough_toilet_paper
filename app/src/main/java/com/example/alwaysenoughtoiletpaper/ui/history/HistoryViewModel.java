package com.example.alwaysenoughtoiletpaper.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Member;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {
     private MutableLiveData<List<HistoryItem>> history;

    public HistoryViewModel() {
        PopulateHistory();
    }

    public LiveData<List<HistoryItem>> getHistory() {
        return history;
    }

    private void PopulateHistory(){
        history = new MutableLiveData<>();
        List<HistoryItem> testHistory = new ArrayList<>();
        testHistory.add(new HistoryItem("Maggie","Bread"));
        testHistory.add(new HistoryItem("Maggie","Eggs"));
        testHistory.add(new HistoryItem("Kim","Butter"));
        testHistory.add(new HistoryItem("Kim","Cucumber"));
        testHistory.add(new HistoryItem("Maggie","Superglue"));
        testHistory.add(new HistoryItem("Kim","Napkins"));

        history.setValue(testHistory);
    }

    public void deleteHistory(){
        history.setValue(new ArrayList<>());
    }
}
