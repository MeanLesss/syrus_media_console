package com.example.syrus_media_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding dashboardBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardBinding =  ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        //To retrieve object in second Activity
        User user = (User) getIntent().getSerializableExtra("loggedUser");

        dashboardBinding.textTest.setText(user.getUsername() + user.getID()+user.getEmail()+user.getPhone());
    }
}