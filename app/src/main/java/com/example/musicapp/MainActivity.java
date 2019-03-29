package com.example.musicapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;
    RelativeLayout main_layout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeBackground();
        initWidgets();
        setupSeekBar();
        setupListener();
    }

    // changing background
    private void changeBackground() {
        // declare Animation and Layout
        main_layout = findViewById(R.id.main_layout);
        animationDrawable = (AnimationDrawable) main_layout.getBackground();

        // Add time changes
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        // Start animation
        animationDrawable.start();
    }

    // MENU  create menu layout:main_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // MENU call action on click on icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // exit icon
        if (item.getItemId() == R.id.action_exit) {
            DialogFragment edf = new ExitDialogFragment();
            edf.show(getSupportFragmentManager(), "Exit Dialog");
        }
        return true;
    }

    // initialize widgets
    private void initWidgets() {
        btnPlayStop = findViewById(R.id.btnPlayStop);
        mediaPlayer = MediaPlayer.create(this, R.raw.dogs);
    }

    // setting base stats from seekBar
    private void setupSeekBar() {
        // setting max value of seekBar
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekVolume = findViewById(R.id.seekVolume);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekVolume.setMax(maxVolume);

        // changing the color of seekBar
        seekVolume.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        seekVolume.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    // setup image button and seekBar listener
    private void setupListener() {
        btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playStopMusic();
            }
        });

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // play and stop music and change button icon
    private void playStopMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            btnPlayStop.setImageResource(R.drawable.ic_action_stop);
            changeVolume();
        } else {
            mediaPlayer.pause();
            btnPlayStop.setImageResource(R.drawable.ic_action_play);
        }
    }

    // change volume on change seekBar
    private void changeVolume(){
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekVolume.setProgress(curVolume);
    }


}
