package com.flickr.photo.test.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flickr.photo.test.R;
import com.squareup.picasso.Picasso;

public class FullScreenFragment extends Fragment {
    public static FullScreenFragment newInstance(String url) {
        return new FullScreenFragment(url);
    }

    private String url;

    public FullScreenFragment(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView imageView = view.findViewById(R.id.fullImage);
        Picasso.get().load(this.url)
//                .fit().centerCrop()
                .placeholder(R.drawable.placeholder).into(imageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
