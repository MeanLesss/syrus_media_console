package com.example.syrus_media_mobile;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.syrus_media_mobile.Models.Video;

public class ViewVideoFragement extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_video_fragement, container, false);
        // Inflate the layout for this fragment
        //        // Create and show the dialog
        String videoUrl = "";
        try {


            Bundle bundle = getArguments();
            Video video = (Video) bundle.getSerializable("selected_video");

            videoUrl = Global_var.FULL_PATH_URL + video.getFile_path().replaceFirst("/", "");

            Log.d("View video part : ", videoUrl);
            // finding videoview by its id
            VideoView videoView = rootView.findViewById(R.id.videoView);

            // sets the resource from the
            // videoUrl to the videoView
            videoView.setVideoPath(videoUrl);

            // creating object of
            // media controller class
            MediaController mediaController = new MediaController(getContext());

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
            videoView.start();
        } catch (Exception e) {
            Log.e("Error View video", e.getMessage());
        }
        // starts the video

        return inflater.inflate(R.layout.fragment_view_video_fragement, container, false);
    }
}