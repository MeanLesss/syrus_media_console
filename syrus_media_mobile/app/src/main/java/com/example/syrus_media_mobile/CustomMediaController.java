package com.example.syrus_media_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class CustomMediaController extends MediaController {
    private ImageButton fullScreenButton;
    private boolean isFullScreen = true;
    private VideoView videoView;
    Activity activity;

    public CustomMediaController(Context context, VideoView videoView,Activity activity) {
        super(context);
        this.videoView = videoView;
        this.activity = activity;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        // Add a full screen button to the media controller
        fullScreenButton = new ImageButton(super.getContext());
        fullScreenButton.setImageResource(R.drawable.baseline_fullscreen_24);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        addView(fullScreenButton, params);

        // Set the OnClickListener for the full screen button
        fullScreenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    // Set the VideoView to normal mode
                    ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    videoView.setLayoutParams(layoutParams);
                    isFullScreen = false;
                    // Rotate the screen to portrait mode
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    // Set the VideoView to full screen mode
                    ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    videoView.setLayoutParams(layoutParams);
                    isFullScreen = true;
                    // Rotate the screen to landscape mode
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
    }
}
