package com.app.gopalayurvediccenter.Activitiestwo;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;

import com.app.gopalayurvediccenter.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String phNumber;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().hasExtra(Constants.WELCOME_BACK) && getIntent() != null) {
            Intent intent = getIntent();
            String welcome_back_msg = intent.getStringExtra(Constants.WELCOME_BACK);
            phNumber = intent.getStringExtra(Constants.PH_NUMBER);
            Log.e(TAG, "onCreate: " + phNumber);
            showToast(welcome_back_msg);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (phNumber != null && phNumber.equals("1234567890")) {
            showToast("Please login to start using the application fully");
        } else {
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_social, R.id.navigation_profile)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);



            /* Color for the icons of bottom navigation*/
            ColorStateList iconColorStates = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{
                            Color.parseColor("#B1B1B3"),
                            Color.parseColor("#009120")
                    });

            navView.setItemIconTintList(iconColorStates);
            navView.setItemTextColor(iconColorStates);

            phNumber = getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "nophNumberfound");

            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(InstanceIdResult -> {
                if (InstanceIdResult != null) {
                    String token = InstanceIdResult;
                    saveFcmTokenToDataBase(token);
                    Log.e(TAG, "onSuccess: " + token);
                }
            });
        }
    }

    private void saveFcmTokenToDataBase(String token) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}