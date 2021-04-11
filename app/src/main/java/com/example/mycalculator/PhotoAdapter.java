package com.example.mycalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context mContext;
    private ArrayList<Bitmap> mListPhotos = new ArrayList<Bitmap>();

    public PhotoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(Bitmap img) {
        this.mListPhotos.add(img);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
            Bitmap bitmap = mListPhotos.get(position);
            holder.imgPhoto.setImageBitmap(bitmap);//каждая ячейка в списке (галлерее) - нужно для вывода

    }

    @Override
    public int getItemCount() {
        if (mListPhotos == null) {
            return 0;
        } else {
            return mListPhotos.size();
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}
