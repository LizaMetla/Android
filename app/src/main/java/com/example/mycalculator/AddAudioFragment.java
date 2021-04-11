package com.example.mycalculator;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddAudioFragment extends Fragment {
    private View view;

    private Button btnSelectImg;
    private Button btnAddImg;
    private TextView textDuration;
    public RecyclerView rcvAudio;
    public AudioAdepter audioAdepter;
    Song selectedSong;
    ImageView imageView;
    private final int PickImage = 1, PickMusic = 2;
    private TextView title, artist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_audio, container, false);

        btnSelectImg = view.findViewById(R.id.selectBtn);
        btnAddImg = view.findViewById(R.id.addBtn);
        imageView = view.findViewById(R.id.image_selected);
        btnAddImg.setEnabled(false);
        artist = view.findViewById(R.id.artist_name_id);
        textDuration = view.findViewById(R.id.duration_id);
        title = view.findViewById(R.id.title_name_id);
        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSong != null) {
                    audioAdepter.setData(selectedSong);
                }
                btnAddImg.setEnabled(false);
                imageView.setImageResource(R.mipmap.ic_launcher);
                view.setVisibility(view.GONE);
                rcvAudio.animate().translationY(-650);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();

            }
        });

        return view;
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openBottomPicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(view.getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openBottomPicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(photoPickerIntent, PickMusic);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent audioReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, audioReturnedIntent);

        if (resultCode == RESULT_OK) {
            String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
            Cursor cursor = getActivity().getContentResolver().query(audioReturnedIntent.getData(),
                    Song.projection, null, null, sortOrder);
            if(cursor!=null&&cursor.moveToFirst()){
                do {
                    selectedSong = new Song(cursor.getLong(0),cursor.getString(1),
                            cursor.getLong(2),cursor.getString(3), cursor.getLong(4), cursor.getString(5),
                            cursor.getInt(6), cursor.getInt(7), cursor.getString(8) );


                }while (cursor.moveToNext());
                if (cursor!=null){
                    cursor.close();
                }
                btnAddImg.setEnabled(true);
                title.setText(selectedSong.title);
                artist.setText(selectedSong.artistName);
                textDuration.setText(Utility.converterDuration(selectedSong.duration));
            }

        }

    }

}