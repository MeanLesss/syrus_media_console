package com.example.syrus_media_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Models.Video;
import com.example.syrus_media_mobile.Service_Api.ServiceApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    String[][] cardData = {
            {"Title 1", "Content 1"},
            {"Title 2", "Content 2"},
            {"Title 3", "Content 3"}
    };
    User user ;
    List<Video> videoList = new ArrayList<Video>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            // Get the arguments
            Bundle args = getArguments();
            if (args != null) {
                user = (User) args.getSerializable("fragUser");
            }
            getAllVideo(user.getID());

//            Toast.makeText(getContext(), "videoList From fragement : " + videoList.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Home fragement error : ", e.getMessage());
        }
        return rootView;
    }

    public void getAllVideo(int user_id){
        String URL = Global_var.FULL_PATH_URL ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi api = retrofit.create(ServiceApi.class);

        Call<ResponseBody> call = api.getAllVideo(user_id);
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
                    try {
                        List<Video> videos = gson.fromJson(response.body().charStream(), videoListType);
                        Log.d("Video", "Video list: " + videos);
                        populateLinearLayout(videos);
                    } catch (Exception e) {
                        Log.e("Video", "Error: " + e.getMessage(), e);
                    }
                } catch (Exception e) {
                    Log.e("Login", "Error: " + e.getMessage(), e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // handle failure
                Log.e("Login", "Error: " + t.getMessage(), t);
            }
        });
    }
    private void populateLinearLayout(List<Video> videos) {
        // Get the LinearLayout
        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout);

        // Loop over the card data
        if (videos != null && videos.size() > 0) {
            for (Video video : videos) {
                Log.d("Videos", "response videos: " + video.getTitle() + video.getId());
                // Inflate the CardView layout
                View cardView = getLayoutInflater().inflate(R.layout.activity_video_card_view, linearLayout, false);
                // Set the CardView's content
                TextView titleTextView = cardView.findViewById(R.id.titleTextView);
                TextView contentTextView = cardView.findViewById(R.id.contentTextView);
                TextView genreTextView = cardView.findViewById(R.id.genreTextView);
                ImageView imageView = cardView.findViewById(R.id.imageView);
//          set the value
                titleTextView.setText(video.getTitle());
                contentTextView.setText(video.getDescription());
                genreTextView.setText(video.getGenre());

                String imageUrl = Global_var.FULL_PATH_URL + video.getThumbnail_path().replaceFirst("/", "");
                Log.d("Image url view", imageUrl);
                Glide.with(getContext())
                        .load(imageUrl)
                        .into(imageView);
                // Set an OnClickListener on the CardView
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the click here
                        Intent intent = new Intent(getActivity(), ViewVideoActivity.class);
                        intent.putExtra("selected_video", video);

                        Toast.makeText(getContext(), video.getTitle(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                });
                // Add the CardView to the LinearLayout
                linearLayout.addView(cardView);
            }
        }
    }

}