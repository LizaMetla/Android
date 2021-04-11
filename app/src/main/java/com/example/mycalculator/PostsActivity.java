package com.example.mycalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {
    PostsAdapter postsAdapter;
    //Button addPostBtn;
//    FloatingActionButton addPostBtn;
    RecyclerView rcvAudio;
    private ArrayList<Post> mListPosts = new ArrayList<Post>();
    private final int resultAdd = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
//        addPostBtn = findViewById(R.id.add_post_btn_id);


        // создаем адаптер
        postsAdapter = new PostsAdapter(getApplicationContext(), mListPosts, new PostsAdapter.RecycleItemClickListener() {
            //обработчик на нажатие поста из списка
            @Override
            public void onClickListener(Post post, int position) {
                Toast.makeText(getApplicationContext(), post.title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PostsActivity.this, DetailPostActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // настраиваем RecyclerView
        //GridLayoutManager упорядочивает элементы в виде грида со столлбцами и строками
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        rcvAudio = findViewById(R.id.rcv_posts);
        rcvAudio.setHasFixedSize(true);
        rcvAudio.setLayoutManager(gridLayoutManager);
        rcvAudio.setFocusable(false);
        // устанавливаем для списка адаптер
        rcvAudio.setAdapter(postsAdapter);

        try {
            postsAdapter.setArray(Utility.getPostsList(getApplicationContext()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        addPostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PostsActivity.this, AddPostActivity.class);
//                startActivityForResult(intent, resultAdd);
//            }
//        });
    }

}