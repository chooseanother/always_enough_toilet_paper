package com.example.alwaysenoughtoiletpaper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );


    private SignInActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        viewModel = new ViewModelProvider(this).get(SignInActivityViewModel.class);

        checkIfSignedIn();
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            handleResult();
        } else {
            if (response == null){
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                signIn();
            } else {
                Log.e("Login", response.getError().getMessage());
            }
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private void goToMainActivity(){
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private void goToJoinCreate(){
        startActivity(new Intent(SignInActivity.this, JoinCreateHouseholdActivity.class));
        finish();
    }

    private void checkIfSignedIn(){
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null){
                // handle result
                handleResult();
            } else {
                signIn();
            }
        });
    }

    private void handleResult(){
        viewModel.initUserInfo();

        viewModel.getCurrentUserInfo().observe(this, userInfo -> {
            String householdId = userInfo.getHouseholdId();
            Log.d("signIn", "Household "+householdId);
            if (householdId == null){

            } else if (householdId.equals("")){
                goToJoinCreate();
            } else {
                // navigate to mainActivity
                goToMainActivity();
            }
        });
    }

    private void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        activityResultLauncher.launch(signInIntent);
    }
}