package com.example.syrus_media_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Service_Api.ServiceApi;
import com.example.syrus_media_mobile.databinding.ActivityMainBinding;
import com.example.syrus_media_mobile.databinding.ActivitySignUpBinding;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonLogIn.setOnClickListener(view -> login());
        binding.buttonSignUp.setOnClickListener(view -> gotoSignUp());
    }

    private void gotoSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }

    private void login() {
        String username = String.valueOf(binding.editTextUsername.getText());
        String password = String.valueOf(binding.editTextPassword.getText());


        User user = new User(username, password);
        String URL = Global_var.FULL_PATH_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi api = retrofit.create(ServiceApi.class);

        Call<ResponseBody> call = api.loginUser(user);
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
                    Toast.makeText(MainActivity.this, userResponse.getUsername(), Toast.LENGTH_SHORT).show();
                    getUserInfo("Successfully log in!", userResponse);
                } catch (Exception e) {
                    Log.e("Login", "Error: " + e.getMessage(), e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // handle failure
                Log.e("Login", "Error: " + t.getMessage(), t);
//                    Toast.makeText(SignUp.this, "Nah error", Toast.LENGTH_SHORT).show();
                getUserInfo(t.getMessage() + call, user);
            }
        });
    }
    void getUserInfo(String message,User user){
        if(user!= null && user.getSuccess() != ""){
            new AlertDialog.Builder(this)
                    .setTitle(user.getSuccess())
//                    .setView()
                    .setPositiveButton("Okay",(dialogInterface, i) -> {
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        //To pass:
                        intent.putExtra("loggedUser", user);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                    .create().show();
        }else {
            assert user != null;
            new AlertDialog.Builder(this)
                    .setTitle(user.getError())
                    //                .setView(customDialogBinding.getRoot())
                    .setPositiveButton("Okay", (dialogInterface, i) -> {
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .create().show();
        }
    }

}