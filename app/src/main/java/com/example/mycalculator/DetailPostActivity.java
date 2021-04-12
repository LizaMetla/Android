package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DetailPostActivity extends AppCompatActivity {
    private Post post;
    private Button backBtn, deleteBtn;
    private ImageView postPhoto;
    private TextView titleText, descriptionText, linkText;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Integer position = (Integer) getIntent().getSerializableExtra("position");
        try {
            ArrayList<Post> mListPosts = Utility.getPostsList(getApplicationContext());
            post = mListPosts.get(position);
            secondServiceCall(post.selectedImagePath);
            backBtn = findViewById(R.id.detail_back_button);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void secondServiceCall(String url)
    {
        // use this var membershipid acc to your need ...
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                postPhoto.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // 100 is your custom Size before Downloading the Image.
        queue.add(ir);
    }

}