package com.example.mycalculator;

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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class FragmentGallery extends Fragment {
    private View view;
    private Button btnOpenAddFragmentImage;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;
    AddImageFragment fa;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //инициализация Recycler View
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        if (photoAdapter == null) {
            photoAdapter = new PhotoAdapter(view.getContext());
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3, LinearLayoutManager.VERTICAL, false);
        rcvPhoto = view.findViewById(R.id.rcv_photo);
        rcvPhoto.setHasFixedSize(true);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setFocusable(false);
        //задаём адаптер
        rcvPhoto.setAdapter(photoAdapter);
        btnOpenAddFragmentImage = view.findViewById(R.id.button_add_image);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));

        //обработка нажатия add
        btnOpenAddFragmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvPhoto.animate().translationY(0);
                view.findViewById(R.id.frame_for_image).getLayoutParams().height = 650;
                fa = new AddImageFragment();//заменям фрагмент
                fa.photoAdapter = photoAdapter;
                fa.rcvPhoto = rcvPhoto;
                FragmentTransaction ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.replace(R.id.frame_for_image, fa);
                ft.commit();

            }
        });

        return view;

    }

}