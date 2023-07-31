package com.example.syrus_media_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.syrus_media_mobile.Models.Video;

public class ViewVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        // finding videoview by its id
        Intent intent = getIntent();
        Video video = (Video) intent.getSerializableExtra("selected_video");
        VideoView videoView = findViewById(R.id.videoView);
        TextView titleTextView =  findViewById(R.id.videoTitle);
        TextView contentTextView =  findViewById(R.id.videoDescription);
        TextView genreTextView =  findViewById(R.id.videoGenre);
//          set the value
        titleTextView.setText(video.getTitle());
        contentTextView.setText(video.getDescription());
        genreTextView.setText("Genre : " + video.getGenre());

        videoUrl =  Global_var.FULL_PATH_URL + video.getFile_path().replaceFirst("/", "");
        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoPath(videoUrl);
        // creating object of
        // media controller class
        Activity activity = (Activity) this;
        MediaController CustomMediaController = new CustomMediaController(this,videoView,activity);
        videoView.setMediaController(CustomMediaController);
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Handle errors here
                return true;
            }
        });
        // starts the video
        videoView.start();
    }
}
