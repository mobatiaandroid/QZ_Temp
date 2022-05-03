package com.whiteteal.quizappfirebase.BackgroundService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.whiteteal.quizappfirebase.R;

/**
 * Created by Arshad on 29,April,2022
 */
public class BackgroundMusic extends Service
{
    private static BackgroundMusic single_instance = null;

    MediaPlayer player;
    // Static method
    // Static method to create instance of Singleton class
    public static BackgroundMusic getInstance()
    {
        if (single_instance == null)
            single_instance = new BackgroundMusic();

        return single_instance;
    }
    public void playMusic()
    {
         player = new MediaPlayer();
        player = MediaPlayer.create(this,  R.raw.isg_bg);
        player.setLooping(true); // Set looping
        player.setVolume(50,50);
        player.start();
    }
    public void pausePlayer() {
        if (player.isPlaying()){
            player.pause();
        }
    }
    public void resumePlayer() {
       player.start();

    }
    public void stopPlayer() {
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
