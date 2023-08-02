package com.example.syrus_media_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syrus_media_mobile.Models.Music;
import com.example.syrus_media_mobile.Models.Video;
import com.example.syrus_media_mobile.R;

import java.io.IOException;

public class ViewMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_music);

        String musicUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp3";
        // finding videoview by its id
        Intent intent = getIntent();
        Music music = (Music) intent.getSerializableExtra("selected_music");
        musicUrl = Global_var.FULL_PATH_URL + music.getFile_path().replaceFirst("/", "");

        ImageView imageView = findViewById(R.id.media_controller_anchor);
        TextView titleTextView =  findViewById(R.id.videoTitle);
        TextView contentTextView =  findViewById(R.id.videoDescription);
        TextView genreTextView =  findViewById(R.id.videoGenre);

        titleTextView.setText(music.getTitle());
        contentTextView.setText(music.getDescription());
        genreTextView.setText("Genre : " + music.getGenre());
        String imageUrl = Global_var.FULL_PATH_URL + music.getThumbnail_path().replaceFirst("/", "");
        Log.d("Image url view", imageUrl);
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

// Set up the MediaPlayer
        final MediaPlayer mediaPlayer = new MediaPlayer();

// Set the audio attributes for the MediaPlayer
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        mediaPlayer.setAudioAttributes(audioAttributes);

        try {
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// Set up the play/pause button
        Button playPauseButton = findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });
        // Set up the SeekBar
        final SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // Update the SeekBar progress periodically
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

}