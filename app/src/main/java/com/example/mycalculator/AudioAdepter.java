package com.example.mycalculator;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.IOException;
import java.util.ArrayList;

public class AudioAdepter extends RecyclerView.Adapter<AudioAdepter.AudioAdepterHolder> {

    private Context mContext;
    private ArrayList<Song> mListMusic = new ArrayList<Song>();
    private RecycleItemClickListener listener;
    private int selectedPosition;
    public AudioAdepter(Context mContext, ArrayList<Song> mListMusic, RecycleItemClickListener listener) {
        this.mContext = mContext;
        this.mListMusic = mListMusic;
        this.listener = listener;
        this.selectedPosition = 0;

    }

    public void setData(Song song) {
        this.mListMusic.add(song);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AudioAdepterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
        return new AudioAdepterHolder(view);
    }
    private static String getCoverArtPath(long albumId, Context context) {

        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }
    @Override
    public void onBindViewHolder(@NonNull AudioAdepterHolder holder, int position) {
        Song song = mListMusic.get(position);
        if(song!=null){
            if(selectedPosition == position){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_on_surface_disabled));
            }
            else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey));
            }
            holder.title.setText(song.title);
            holder.artist.setText(song.artistName);
            holder.duration.setText(Utility.converterDuration(song.duration));
            holder.imgPhoto.setImageBitmap(BitmapFactory.decodeFile(getCoverArtPath(song.albumId, mContext)));
            ImageLoader.getInstance().displayImage(getImage(song.albumId).toString(), holder.imgPhoto,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.mipmap.ic_launcher).resetViewBeforeLoading(true).build());
            holder.bind(song, listener);
        }
    }

    private Uri getImage(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    @Override
    public int getItemCount() {
        if (mListMusic == null) {
            return 0;
        } else {
            return mListMusic.size();
        }
    }

    public class AudioAdepterHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView title, artist, duration;
        public AudioAdepterHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            artist = itemView.findViewById(R.id.artist_name_id);
            title = itemView.findViewById(R.id.title_name_id);
            duration = itemView.findViewById(R.id.duration_id);
        }
        public void bind(Song song, RecycleItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(song, getLayoutPosition());
                }
            });
        }
    }
    public interface RecycleItemClickListener{
        void onClickListener(Song song, int position);

    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
    public int getSelectedPosition(){
        return selectedPosition;
    }
}
