package com.example.syrus_media_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.syrus_media_mobile.databinding.ActivityMainBinding;
import com.example.syrus_media_mobile.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonCancel.setOnClickListener(view->gotoLogin());
    }
    private void gotoLogin() {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        String url = Global_var.FULL_PATH_URL;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}