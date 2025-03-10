package com.example.alwaysenoughtoiletpaper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alwaysenoughtoiletpaper.databinding.ActivityMainBinding;
import com.example.alwaysenoughtoiletpaper.data.UserRepository;
import com.example.alwaysenoughtoiletpaper.model.UserInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("testShop","onCreate");
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_shopping_list, R.id.nav_members, R.id.nav_history, R.id.nav_payments, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        bindLogOutMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void checkIfSignedIn(){

        LiveData<FirebaseUser> currentUser = viewModel.getCurrentUser();
        currentUser.observe(this, user -> {
            if (user != null){
                viewModel.init();
                setUpMenuUsername();
                viewModel.getUserInfo().observe(this, userInfo -> {
                    if (userInfo == null){
                        String name = currentUser.getValue().getDisplayName();
                        viewModel.saveUserInfo(name, "", "");
                    }
                    String householdId = userInfo.getHouseholdId();
                    Log.d("testShopUserInfoObserve","householdid "+householdId);
                    if (householdId != null){
                        if (!householdId.equals("")){
                            viewModel.initHouseholdRepository(householdId);
                            Log.d("testShopHouse","initialized");
                        }
                    }
                });
            } else {
                startLoginActivity();
            }
        });
    }

    private void bindLogOutMenu(){
        binding.menuLogOut.setOnClickListener(view -> {
            signOut();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private void signOut() {
        viewModel.signOut();
    }

    private void setUpMenuUsername(){
        TextView username = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_name);
        username.setText(viewModel.getCurrentUser().getValue().getDisplayName());
        TextView email = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_email);
        email.setText(viewModel.getCurrentUser().getValue().getEmail());
        ImageView image = binding.navView.getHeaderView(0).findViewById(R.id.menu_user_image);
        Uri photoUrl = viewModel.getCurrentUser().getValue().getPhotoUrl();
        Glide.with(MainActivity.this).load(photoUrl).into(image);
    }
}