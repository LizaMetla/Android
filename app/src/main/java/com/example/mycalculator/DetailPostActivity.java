package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity {
    private Post post;
    private Button backBtn, deleteBtn;
    private ImageView postPhoto;
    public MediaPlayer mediaPlayer;
    private TextView musicText, titleText, descriptionText, linkText;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Integer position = (Integer) getIntent().getSerializableExtra("position");
        try {
            ArrayList<Post> mListPosts = Utility.getPostsList(getApplicationContext());
            post = mListPosts.get(position);

            backBtn = findViewById(R.id.detail_back_button);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer!=null & mediaPlayer.isPlaying()){ //если хотим запустить песню которая играет
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    finish();
                }
            });

            deleteBtn = findViewById(R.id.detail_delete_button);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Utility.deletePostInList(getApplicationContext(), position);
                        Intent intent = new Intent(DetailPostActivity.this, PostsActivity.class);
                        startActivity(intent);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Sorry, I can't delete this post", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            postPhoto = findViewById(R.id.detail_photo_card);
            postPhoto.setImageBitmap(post.image);

            musicText = findViewById(R.id.detail_play_music);
            musicText.setText(post.song.title);

            titleText = findViewById(R.id.detail_title);
            titleText.setText(post.title);

            descriptionText = findViewById(R.id.detail_description);
            descriptionText.setText(post.description);

            linkText = findViewById(R.id.detail_link);
            linkText.setText(post.link);
            linkText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailPostActivity.this, WebViewActivity.class);
                    intent.putExtra("link", post.link);
                    startActivity(intent);
                }
            });
            mediaPlayer = new MediaPlayer();
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
            musicText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else if (mediaPlayer.getDuration() != 0) {
                        mediaPlayer.start();//после добавления 1 песни
                    } else {
                        prepareSong(post.song);//заносим музыку в память
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void prepareSong(Song song){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song.path);
            mediaPlayer.prepareAsync();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer!=null & mediaPlayer.isPlaying()){ //если хотим запустить песню которая играет
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
}