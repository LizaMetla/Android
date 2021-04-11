package com.example.mycalculator;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentMusic extends Fragment {
    private View view;
    private Button btnOpenAddFragmentImage;
    private RecyclerView rcvAudio;
    private AudioAdepter audioAdapter;
    private AddAudioFragment fam;
    public ArrayList<Song> mListMusic;
    private int currentIndex;
    public MediaPlayer mediaPlayer;
    private long currentSongLength;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListMusic = new ArrayList<Song>();//список объектов song
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music, container, false);
        if (audioAdapter == null) {
            audioAdapter = new AudioAdepter(view.getContext(), mListMusic, new AudioAdepter.RecycleItemClickListener() {
                //обработчик на нажатие песни из списка
                @Override
                public void onClickListener(Song song, int position) {
                    Toast.makeText(view.getContext(), song.title, Toast.LENGTH_SHORT).show();

                    if (currentIndex == position){//
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        }else if(mediaPlayer.getDuration() != 0) {
                            mediaPlayer.start();//после добавления 1 песни
                        }else {
                            prepareSong(song);//заносим музыку в память
                        }
                    }else {
                        prepareSong(song);//готовим песню к вопроизведению
                    }
                    ChangeSelectedSong(position);

                }
            });
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, LinearLayoutManager.VERTICAL, false);
        rcvAudio = view.findViewById(R.id.rcv_music);
        rcvAudio.setHasFixedSize(true);
        rcvAudio.setLayoutManager(gridLayoutManager);
        rcvAudio.setFocusable(false);
        rcvAudio.setAdapter(audioAdapter);
        btnOpenAddFragmentImage = view.findViewById(R.id.button_add_music);

        btnOpenAddFragmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                rcvAudio.animate().translationY(0);
                view.findViewById(R.id.frame_for_music).getLayoutParams().height = 800;
                fam = new AddAudioFragment();
                fam.audioAdepter = audioAdapter;
                fam.rcvAudio = rcvAudio;
                FragmentTransaction ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.replace(R.id.frame_for_music, fam);
                ft.commit();

            }
        });

        return view;
    }
    private void ChangeSelectedSong(int index){
        audioAdapter.notifyItemChanged(audioAdapter.getSelectedPosition());
        currentIndex = index;
        audioAdapter.setSelectedPosition(currentIndex);
        audioAdapter.notifyItemChanged(currentIndex);
    }
    private void prepareSong(Song song){
        currentSongLength = song.duration;
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song.path);
            mediaPlayer.prepareAsync();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}