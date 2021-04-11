package com.example.mycalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import static android.app.Activity.RESULT_OK;

public class AddImageFragment extends Fragment {
    private View view;

    private Button btnSelectImg;
    private Button btnAddImg;
    public RecyclerView rcvPhoto;
    public PhotoAdapter photoAdapter;
    private final int PickImage = 1, PickMusic = 2;
    Bitmap selectedImage;
    ImageView imageView;
    AddImageFragment AddGallery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_image, container, false);
        AddGallery = this;
        btnSelectImg = view.findViewById(R.id.selectBtn);
        btnAddImg = view.findViewById(R.id.addBtn);
        imageView = view.findViewById(R.id.image_selected);
        btnAddImg.setEnabled(false);
        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage != null) {
                    photoAdapter.setData(selectedImage);//выбранную картинку загружаем в mlistphoto (в phototadapter)
                }
                btnAddImg.setEnabled(false);
                imageView.setImageResource(R.mipmap.ic_launcher);
                view.setVisibility(view.GONE);
                rcvPhoto.animate().translationY(-650);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();//запрос на выбор из галлереи

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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");//тип файла, который мы будем загружать

        startActivityForResult(photoPickerIntent, PickImage);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode == RESULT_OK) { //когдаа выбрал фоточку и нажал ок
            try {
                ContentResolver contentResolver = getActivity().getContentResolver();
                final Uri imageUri = imageReturnedIntent.getData(); //выбранный объект интента - из него берём uri изображения
                final InputStream imageStream = contentResolver.openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);//из потока байтов изображения и конвертируем в объект bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                //получение изображения из файловой системы смартфона
                selectedImage = bitmap;//в переменную класса мы загружаем выбранную картинку
                imageView.setImageBitmap(selectedImage);//изначальную картинку заменяем на выбранную
                btnAddImg.setEnabled(true);//делаем доступной кнопку добавления
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}