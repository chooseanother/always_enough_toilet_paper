package com.example.alwaysenoughtoiletpaper.model;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.alwaysenoughtoiletpaper.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HouseholdLiveData extends LiveData<Household> {
    private DatabaseReference databaseReference;

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Household household = snapshot.getValue(Household.class);
            setValue(household);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public HouseholdLiveData(DatabaseReference ref){
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }
}
