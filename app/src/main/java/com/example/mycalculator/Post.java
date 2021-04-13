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
    public static String getDescriptionFromServer(JSONObject response) throws JSONException {
        return ((response.has("description") && !response.isNull("description"))) ? response.getString("description") : null;
    }
    public String getPostDescription(){
        if(this.description == null || this.description.isEmpty()){
            return "Цена: " + this.price + ((this.owner) ? "\nСобственник" : "\nАгентство");
        }else {
            return this.description;
        }
    }
    public static Post getPostFromJson(JSONObject jo, Context context) throws JSONException {
        return new Post(null,
                jo.getString("selectedImagePath"), getDescriptionFromServer(jo), jo.getString("title"),
                jo.getString("link"), jo.getBoolean("owner"), jo.getDouble("price"), jo.getString("currency"));
    }

}
