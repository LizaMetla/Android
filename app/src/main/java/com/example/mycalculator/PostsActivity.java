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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String maxPrice = "1000";
        String minPrice = "200";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://r.onliner.by/sdapi/ak.api/search/apartments?price%5Bmin%5D=" + minPrice + "&price%5Bmax%5D=" + maxPrice + "&currency=usd&bounds%5Blb%5D%5Blat%5D=53.765346858917425&bounds%5Blb%5D%5Blong%5D=27.413028708853112&bounds%5Brt%5D%5Blat%5D=54.03091474781306&bounds%5Brt%5D%5Blong%5D=27.711908525658554&page=1&v=0.15562999284261325";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseString) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject response = new JSONObject(responseString);
                            JSONArray jsonArray = response.getJSONArray("apartments");
                            for (int i = 0; i < 10; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String photoUrl = jsonObject.getString("photo");
                                Bitmap image = null;
                                String title = jsonObject.getJSONObject("location").getString("address");
                                String link = jsonObject.getString("url");
                                Boolean owner = jsonObject.getJSONObject("contact").getBoolean("owner");
                                Double price = jsonObject.getJSONObject("price").getDouble("amount");
                                String currency = jsonObject.getJSONObject("price").getString("currency");
                                String description = "Цена: " + price + ((owner) ? "\nСобственник" : "Агентство");
                                mListPosts.add(new Post(image, photoUrl, description, title, link, owner, price, currency));
                                secondServiceCall(mListPosts.size() - 1, photoUrl);
                            }
                            postsAdapter.setArray(mListPosts);
                            Utility.savePostsInFile(getApplicationContext(), mListPosts);
                            postsAdapter.notifyDataSetChanged();
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postsAdapter.setArray(mListPosts);
            }
        }){

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("accept", "application/json, text/plain, */*");
                return headers;
            }};
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
            mListPosts = Utility.getPostsList(getApplicationContext());
            if(mListPosts.size() == 0){
                queue.add(stringRequest);
            }
            postsAdapter.setArray(mListPosts);
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
    public void secondServiceCall(int membershipid,String url)
    {
        // use this var membershipid acc to your need ...
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                Post post = mListPosts.get(membershipid);
                post.image = response;
                mListPosts.set(membershipid, post);
                postsAdapter.setArray(mListPosts);
                postsAdapter.notifyDataSetChanged();

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postsAdapter.setArray(mListPosts);
            }
        });
        // 100 is your custom Size before Downloading the Image.
        queue.add(ir);
    }
}