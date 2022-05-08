package com.example.alwaysenoughtoiletpaper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alwaysenoughtoiletpaper.model.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivityViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public SignInActivityViewModel(@NonNull Application application) {
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
