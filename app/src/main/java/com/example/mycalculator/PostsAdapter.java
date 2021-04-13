package com.example.mycalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import androidx.core.content.ContextCompat;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsAdepterHolder> {

    private Context mContext;
    private ArrayList<Post> mListPosts = new ArrayList<Post>();
    private RecycleItemClickListener listener;
    private int selectedPosition;

    // Данные передаются в конструктор
    public PostsAdapter(Context mContext, ArrayList<Post> mListPosts, RecycleItemClickListener listener) {
        this.mContext = mContext;
        this.mListPosts = mListPosts;
        this.listener = listener;
        this.selectedPosition = 0;

    }
    public List<Post> getList(){
        return this.mListPosts;
    }
    public void setData(Post post) {
        this.mListPosts.add(post);
        notifyDataSetChanged();
    }
    public void setArray(ArrayList<Post> mListPosts){
        this.mListPosts = mListPosts;
    }
    //наполнение ячейки из xml
    @NonNull
    @Override
    public PostsAdepterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostsAdepterHolder(view);
    }
    // привязывает данные к TextView в каждой ячейке
    @Override
    public void onBindViewHolder(@NonNull PostsAdepterHolder holder, int position) {
        Post post = mListPosts.get(position);
        if(post!=null){
            holder.title.setText(post.title);
            holder.imgPhoto.setImageBitmap(post.image);
            holder.link.setText(post.link);
            holder.description.setText(post.getPostDescription());
            holder.bind(post, listener);
        }
    }

    //количество ячеек
    @Override
    public int getItemCount() {
        if (mListPosts == null) {
            return 0;
        } else {
            return mListPosts.size();
        }
    }

    // сохраняет и перерабатывает представления по мере их прокрутки за пределы экрана
    public class PostsAdepterHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView title, artist, duration, description, link;
        public PostsAdepterHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.postImage);
            title = itemView.findViewById(R.id.titlePostText);
            description = itemView.findViewById(R.id.descriptionPostText);
            link = itemView.findViewById(R.id.linkPostText);

        }
        // позволяет перехватывать события кликов
        public void bind(Post post, RecycleItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(post, getLayoutPosition());
                }
            });
        }
    }
    // родительская активность будет реализовывать этот метод для ответа на события щелчка
    public interface RecycleItemClickListener{
        void onClickListener(Post post, int position);

    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
    public int getSelectedPosition(){
        return selectedPosition;
    }
}
