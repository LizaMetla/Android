package com.example.mycalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.net.URI;

public class Post implements Serializable {
    public final Bitmap image;
    public final String description;
    public final String title;
    public final String link;
    public final String selectedImagePath;
    public final Song song;

    public Post(Bitmap image, String selectedImagePath, String description, String title, String link, Song song) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.link = link;
        this.song = song;
        this.selectedImagePath = selectedImagePath;
    }

    public JSONObject toJson(Context context){
        JSONObject postJson = new JSONObject();
        try {
            postJson.put("selectedImagePath", this.selectedImagePath);
            postJson.put("description", this.description);
            postJson.put("title", this.title);
            postJson.put("link", this.link);
            postJson.put("song", this.song.toJson());
        }catch ( JSONException e){
            e.printStackTrace();
        }

        return postJson;
    }
    public static Post getPostFromJson(JSONObject jo, Context context) throws JSONException {
        return new Post(Post.getBitmapFromString(jo.getString("selectedImagePath"), context),jo.getString("selectedImagePath") ,jo.getString("description"), jo.getString("title"), jo.getString("link"), Song.getSongFromJson((JSONObject) jo.get("song"), context) );
    }
    public static Bitmap getBitmapFromString(String stringPicture, Context context) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(new File(stringPicture)));
        } catch (Exception e){
            return BitmapFactory.decodeFile(stringPicture);
        }

    }
}
