package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MediaActivity extends AppCompatActivity {
    FragmentGallery fg;
    FragmentMusic fm;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        fg = new FragmentGallery();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //когда музыка готова к запуску - подготовка
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mp.isPlaying()){ //если хотим запустить песню которая играет
                    mp.stop();
                    mp.reset();
                }else {
                    mp.start();//запускаем
                }
            }
        });
        fm = new FragmentMusic();
        fm.mediaPlayer = mediaPlayer;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fg);
        ft.commit();
    }


    public void onClickGallery(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fg);
        ft.commit();
    }
    public void onClickPlayer(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fm);
        ft.commit();
    }
}