package com.example.syrus_media_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Models.Video;
import com.example.syrus_media_mobile.Service_Api.ServiceApi;
import com.example.syrus_media_mobile.databinding.ActivityDashboardBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {
    User global_user;
    ActivityDashboardBinding dashboardBinding;
    User user ;
    List<Video> videoList = new ArrayList<Video>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        //To retrieve object in second Activity
        user = (User) getIntent().getSerializableExtra("loggedUser");
//        getAllVideo();

        Toast.makeText(Dashboard.this, "Welcome " + user.getUsername() + user.getEmail(), Toast.LENGTH_SHORT).show();
        // Create a new instance of your fragment
        HomeFragment homeFragment = new HomeFragment();
        // Create a Bundle to hold the arguments
        Bundle args = new Bundle();
        args.putSerializable("fragUser", user);
        // Set the arguments on the fragment
        homeFragment.setArguments(args);

        replaceFragment(homeFragment);
        dashboardBinding.bottomNavigationView.setBackground(null);
        dashboardBinding.uploadButton.setOnClickListener(item->{
            Intent intent = new Intent(Dashboard.this, UploadVideo.class);
            intent.putExtra("loggedUser", user);
            startActivity(intent);
        });

        dashboardBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(homeFragment);
            } else if (itemId == R.id.video) {
                replaceFragment(new VideoFragment());
            } else if (itemId == R.id.music) {
                replaceFragment(new MusicFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
//        dashboardBinding.textTest.setText(user.getUsername() + user.getID() + user.getEmail() + user.getPhone());
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    public void getAllVideo(){
        String URL = Global_var.FULL_PATH_URL ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi api = retrofit.create(ServiceApi.class);

        Call<ResponseBody> call = api.getAllVideo(user.getID());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // handle the response
                try {
                    Gson gson = new Gson();
                    Type videoListType = new TypeToken<List<Video>>(){}.getType();
                    // Check the status code
                    int statusCode = response.code();
                    Log.d("Login", "Status code: " + statusCode);
                    // Print out the raw response data
                    String rawResponse = response.raw().toString();
                    Log.d("Login", "Raw response: " + rawResponse);
//                    String json = response.body().string();
//                    Log.d("Login", "JSON: " + json);

                    try {
                        videoList = gson.fromJson(response.body().charStream(), videoListType);
                        Log.d("Login", "Video list: " + videoList);
                    } catch (Exception e) {
                        Log.e("Login", "Error: " + e.getMessage(), e);
                    }
//                    dashboardBinding.videoCount.setText(String.valueOf(videoList.size()));

                    Log.e("Video list", "response video: " + videoList);
                } catch (Exception e) {
                    Log.e("Login", "Error: " + e.getMessage(), e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // handle failure
                Log.e("Login", "Error: " + t.getMessage(), t);
//                    Toast.makeText(SignUp.this, "Nah error", Toast.LENGTH_SHORT).show();
//                getUserInfo(t.getMessage() + call, user);
            }
        });
    }
    void getUserInfo(String message,User user){
        if(user!= null && user.getSuccess() != ""){
            new AlertDialog.Builder(this)
                    .setTitle(user.getSuccess())
//                    .setView()
                    .setPositiveButton("Okay",(dialogInterface, i) -> {
//                        Intent intent = new Intent(Dashboard.this, Dashboard.class);
//                        //To pass:
//                        intent.putExtra("loggedUser", user);
//                        startActivity(intent);
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