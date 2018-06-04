package com.example.farid.codersappdemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.farid.codersappdemo.login.signin_activity;

public class splash_screen extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);

        getSupportActionBar().hide();

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splashvideo);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.start();
    }
    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, signin_activity.class));
        finish();
    }
}
