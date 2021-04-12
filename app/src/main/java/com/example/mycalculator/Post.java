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
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;

public class Post implements Serializable {
    public Bitmap image;
    public final String description;
    public final String title;
    public final String link;
    public final String selectedImagePath;
    public final Boolean owner;
    public final Double price;
    public final String currency;


    public Post(Bitmap image, String selectedImagePath, String description, String title, String link, Boolean owner, Double price, String currency) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.link = link;
        this.selectedImagePath = selectedImagePath;
        this.owner = owner;
        this.price = price;
        this.currency = currency;

    }

    public JSONObject toJson(Context context) {
        JSONObject postJson = new JSONObject();
        try {
            postJson.put("selectedImagePath", this.selectedImagePath);
            postJson.put("description", this.description);
            postJson.put("title", this.title);
            postJson.put("link", this.link);
            postJson.put("owner", this.owner);
            postJson.put("price", this.price);
            postJson.put("currency", this.currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postJson;
    }

    public static Post getPostFromJson(JSONObject jo, Context context) throws JSONException {
        return new Post(null,
                jo.getString("selectedImagePath"), jo.getString("description"), jo.getString("title"),
                jo.getString("link"), jo.getBoolean("owner"), jo.getDouble("price"), jo.getString("currency"));
    }

    public static Bitmap getBitmapFromString(String stringPicture, Context context) {
        try {
            InputStream in = new java.net.URL(stringPicture).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            return BitmapFactory.decodeFile(stringPicture);
        }

    }
}
