package com.example.mycalculator;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class Song implements Serializable {
    public final long id;
    public final String title;
    public final long artistId;
    public final long albumId;
    public final String artistName;
    public final String albumName;
    public final int duration;
    public final int trackNumber;
    public final String path;
    public static String[] projection = new String[]{
            "_id",
            "title",
            "album_id",
            "album",
            "artist_id",
            "artist",
            "duration",
            "track",
            MediaStore.Audio.Media.DATA
    };
    public Song() {
        id = -1;
        title = "";
        artistId = -1;
        artistName = "";
        duration = -1;
        trackNumber = -1;
        albumId = -1;
        albumName = "";
        path = "";
    }

    public Song(long id, String title, long albumId, String albumName, long artistId,
                String artistName, int duration, int trackNumber, String path) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.artistName = artistName;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.albumName = albumName;
        this.albumId = albumId;
        this.path = path;
    }

    public static Song getSongFromJson(JSONObject jo, Context context) throws JSONException {
        return new Song(jo.getLong("id"), jo.getString("title"),jo.getInt("albumId"),jo.getString("artistName"), jo.getLong("artistId"),
                jo.getString("artistName"), jo.getInt("duration"), jo.getInt("trackNumber"), jo.getString("path"));
    }

    public JSONObject toJson() {
        JSONObject songJson = new JSONObject();
        try {
            songJson.put("id", this.id);
            songJson.put("title", this.title);
            songJson.put("artistId", this.artistId);
            songJson.put("artistName", this.artistName);
            songJson.put("duration", this.duration);
            songJson.put("trackNumber", this.trackNumber);
            songJson.put("albumName", this.albumName);
            songJson.put("albumId", this.albumId);
            songJson.put("path", this.path);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songJson;
    }
}
