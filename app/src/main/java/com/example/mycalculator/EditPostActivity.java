package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class EditPostActivity extends AppCompatActivity {
    private ArrayList<Post> myList;
    private Button saveBtn, backBtn;
    private ImageView imagePost;
    private TextView addMusicText, imagePostUrl;
    private EditText titleText, descriptionText, linkText, priceText, valuteText;
    private CheckBox checkBoxIsOwner;
    Bitmap selectedImage;
    private Song song;
    Post post;
    String selectedImagePath;
    Integer position;
    Button postSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        titleText = findViewById(R.id.edit_title);
        priceText = findViewById(R.id.editPrice);
        valuteText = findViewById(R.id.editValute);
        descriptionText = findViewById(R.id.editDescription);
        linkText = findViewById(R.id.editLink);
        imagePost = findViewById(R.id.flat_photo);
        imagePostUrl = findViewById(R.id.edit_url_link);

        checkBoxIsOwner = findViewById(R.id.checkBoxOwner);

        backBtn = findViewById(R.id.add_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn = findViewById(R.id.edit_confirm_save);
        position = (Integer) getIntent().getSerializableExtra("position");
        try {
            ArrayList<Post> mListPosts = Utility.getPostsList(getApplicationContext());
            post = mListPosts.get(position);
            titleText.setText(post.title);
            priceText.setText(String.valueOf(post.price));

            linkText.setText(post.link);
            imagePostUrl.setText(post.selectedImagePath);
            descriptionText.setText(post.description);
            checkBoxIsOwner.setChecked(post.owner);
            valuteText.setText(post.currency);
            secondServiceCall(post.selectedImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePostUrl.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You are stupid ", Toast.LENGTH_SHORT).show();
                } else if (titleText.getText().toString().isEmpty()) {
                    showError(titleText, "Title is not valid!");
                } else if (linkText.getText().toString().isEmpty()) {
                    showError(linkText, "Link is not valid!");
                } else {
                    post = new Post(selectedImage, imagePostUrl.getText().toString(), descriptionText.getText().toString(),
                            titleText.getText().toString(), linkText.getText().toString(), checkBoxIsOwner.isChecked(),
                            Double.parseDouble(priceText.getText().toString()), valuteText.getText().toString());
                    try {
                        Utility.savePostInList(getApplicationContext(), post, position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(EditPostActivity.this, PostsActivity.class);
                    startActivity(intent);

                }
            }
        });

    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }

    public void secondServiceCall(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                imagePost.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(ir);
    }
}