package com.example.syrus_media_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.syrus_media_mobile.Models.Video;

public class ViewVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        // finding videoview by its id
        VideoView videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        Video video = (Video) intent.getSerializableExtra("selected_video");

        videoUrl =  Global_var.FULL_PATH_URL + video.getFile_path().replaceFirst("/", "");

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoPath(videoUrl);

        // creating object of
        // media controller class
        MediaController mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

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