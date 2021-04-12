package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {
    private ArrayList<Post> myList;
    private Button saveBtn, backBtn;
    private ImageView imagePost;
    private TextView addMusicText;
    private EditText titleText, descriptionText, linkText;
    private final int PickImage = 1, PickMusic = 2;
    Bitmap selectedImage;
    private Song song;
    Post post;
    String selectedImagePath;

    Button postSaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
//        myList = (ArrayList<Post>) getIntent().getSerializableExtra("data");
        postSaveBtn = findViewById(R.id.post_save_btn_id);
        addMusicText = findViewById(R.id.textViewMusic);
        titleText = findViewById(R.id.editTitle);
        descriptionText = findViewById(R.id.editDescription);
        linkText = findViewById(R.id.linkTitle);
        imagePost = findViewById(R.id.person_photo_new_card);

        backBtn = findViewById(R.id.add_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn = findViewById(R.id.post_save_btn_id);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song == null){
                    Toast.makeText(getApplicationContext(), "You are stupid ", Toast.LENGTH_SHORT).show();
                } else if (selectedImage == null) {
                    Toast.makeText(getApplicationContext(), "You are stupid ", Toast.LENGTH_SHORT).show();
                } else if (titleText.getText().toString().isEmpty()) {
                    showError(titleText, "Title is not valid!");
                } else if (descriptionText.getText().toString().isEmpty()) {
                    showError(titleText, "Description is not valid!");
                } else if (linkText.getText().toString().isEmpty()) {
                    showError(linkText, "Link is not valid!");
                } else {
                    post = new Post(selectedImage, selectedImagePath, descriptionText.getText().toString(), titleText.getText().toString(), linkText.getText().toString(), false, 0.0, "USD");
                    try {
                        Utility.savePostInList(getApplicationContext(),post);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(AddPostActivity.this, PostsActivity.class);
                    startActivity(intent);

                }
            }
        });

        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionPhoto();
            }
        });

        addMusicText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
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
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions( Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openBottomPicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoPickerIntent, PickMusic);

    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case(PickMusic):
                    String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
                    Cursor cursor = this.getContentResolver().query(returnedIntent.getData(),
                            Song.projection, null, null, sortOrder);
                    if(cursor!=null&&cursor.moveToFirst()){
                        do {
                            song = new Song(cursor.getLong(0),cursor.getString(1),
                                    cursor.getLong(2),cursor.getString(3), cursor.getLong(4), cursor.getString(5),
                                    cursor.getInt(6), cursor.getInt(7), cursor.getString(8) );
                        }while (cursor.moveToNext());
                        if (cursor!=null){
                            cursor.close();
                        }
                        addMusicText.setText(song.title);
                    }
                    break;
                case(PickImage):
                    try {
                        ContentResolver contentResolver = this.getContentResolver();
                        final Uri imageUri = returnedIntent.getData(); //выбранный объект интента - из него берём uri изображения
                        final InputStream imageStream = contentResolver.openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);//из потока байтов изображения и конвертируем в объект bitmap
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                        //получение изображения из файловой системы смартфона
                        selectedImagePath = getRealPathFromURI(imageUri);

                        int height = selectedImage.getHeight();
                        int width = selectedImage.getWidth();
                        float mult = (float) width / height;
                        selectedImage = Bitmap.createScaledBitmap(bitmap, 700, (int) (700 / mult), true);

//                        selectedImage = bitmap;//в переменную класса мы загружаем выбранную картинку
                        imagePost.setImageBitmap(selectedImage);//изначальную картинку заменяем на выбранную

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }
    private void requestPermissionPhoto() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openBottomPickerPhoto();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openBottomPickerPhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");//тип файла, который мы будем загружать

        startActivityForResult(photoPickerIntent, PickImage);

    }
    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }
}