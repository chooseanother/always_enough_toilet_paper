package com.example.alwaysenoughtoiletpaper.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwaysenoughtoiletpaper.model.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class HomeViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void signOut(){
        userRepository.signOut();
    }
}