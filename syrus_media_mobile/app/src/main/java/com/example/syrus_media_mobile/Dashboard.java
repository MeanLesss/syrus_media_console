package com.example.syrus_media_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    List<Video> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        //To retrieve object in second Activity
        user = (User) getIntent().getSerializableExtra("loggedUser");
        getAllVideo();

        dashboardBinding.textTest.setText(user.getUsername() + user.getID() + user.getEmail() + user.getPhone());

        //        if (videoList.size() > 0) {
//            for (Video video : videoList) {
//                TextView textView = new TextView(this);
//                textView.setText(String.format("%s%d", video.getTitle(), video.getId()));
//                dashboardBinding.constraintLay.addView(textView);
//            }
//        }
    }
    public void getAllVideo(){
//        + "?user_id=" + user.getID()
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
                    videoList = gson.fromJson(response.body().charStream(), videoListType);
                    dashboardBinding.videoCount.setText(videoList.size());
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