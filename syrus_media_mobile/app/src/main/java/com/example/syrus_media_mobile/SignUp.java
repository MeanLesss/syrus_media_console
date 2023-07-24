package com.example.syrus_media_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.syrus_media_mobile.Service_Api.ServiceApi;
import com.example.syrus_media_mobile.databinding.ActivitySignUpBinding;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
//import retrofit2.converter.gson.GsonConverterFactory;


import com.example.syrus_media_mobile.Models.User;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonCancel.setOnClickListener(view->gotoLogin());
        binding.buttonConfirmSignUp.setOnClickListener(view->SignUp());
    }
    private void gotoLogin() {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        String url = Global_var.FULL_PATH_URL;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotConstructor")
    public void SignUp() {
        String username = String.valueOf(binding.editTextUsername.getText());
        String password = String.valueOf(binding.editTextPassword.getText());
        String confirmPass = String.valueOf(binding.editTextConfirmPassword.getText());
        String email = String.valueOf(binding.editTextEmail.getText());
        String phone = String.valueOf(binding.editTextPhone.getText());

        if(password.equals(confirmPass)){
            User user = new User(username, password, confirmPass, email, phone);
            String CONTROLLER = "SignUpController/";
            String URL = Global_var.FULL_PATH_URL;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServiceApi api = retrofit.create(ServiceApi.class);

            Call<ResponseBody> call = api.createUser(user);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    // handle the response
                    try {
                        Gson gson = new Gson();
                        JsonReader jsonReader = new JsonReader(response.body().charStream());
                        jsonReader.setLenient(true);
                        User userResponse = gson.fromJson(jsonReader, User.class);
    //                    Log.e("SignUp", "response: " + jsonReader);
                        Toast.makeText(SignUp.this, userResponse.getUsername(), Toast.LENGTH_SHORT).show();
                            getUserInfo("Successfully sign up!",userResponse);
//                        if(userResponse.getUsername() != ""){
                            getUserInfo("Successfully sign up!",userResponse);
//                        }else {
//                            getUserInfo("Sign up failed!",userResponse);
//                        }
                    } catch (Exception e) {
                        Log.e("SignUp", "Error: " + e.getMessage(), e);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // handle failure
                    Log.e("SignUp", "Error: " + t.getMessage(), t);
//                    Toast.makeText(SignUp.this, "Nah error", Toast.LENGTH_SHORT).show();
                    getUserInfo(t.getMessage() + call, user);
                }
            });
        }else{
            getUserInfo("The password doesn't match!",new User().setError("Password doesn't match!"));
        }

    }

    void getUserInfo(String message,User user){
        ActivitySignUpBinding customDialogBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        if(user!= null && user.getSuccess() != ""){
            new AlertDialog.Builder(this)
                    .setTitle(user.getSuccess())
    //                .setView(customDialogBinding.getRoot())
                    .setPositiveButton("Okay",(dialogInterface, i) -> {})
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                    .create().show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle(user.getError())
                    //                .setView(customDialogBinding.getRoot())
                    .setPositiveButton("Okay",(dialogInterface, i) -> {})
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                    .create().show();
        }
    }
}