package com.flickr.photo.test.controllers;

import android.util.Log;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.flickr.photo.test.adapter.PostRecyclerAdapter;
import com.flickr.photo.test.constants.Urls;
import com.flickr.photo.test.models.Gallery;
import com.flickr.photo.test.models.Photo;
import com.flickr.photo.test.network.APIRequests;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author Humam Alayad
 */
@SuppressWarnings("deprecation")
public class GalleryController implements Callback<Gallery> {
    private PostRecyclerAdapter adapter;
    private ArrayList<Photo> photoList;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isLoad = false;

    public boolean isLoading() {
        return isLoad;
    }

    public GalleryController(PostRecyclerAdapter adapter, SwipeRefreshLayout swipeRefresh) {
        this.adapter = adapter;
        this.swipeRefresh = swipeRefresh;
    }

    public void getNewElements(int perPage, int page) {
        photoList = new ArrayList<Photo>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        APIRequests apiRequests = retrofit.create(APIRequests.class);

        Call<Gallery> call = apiRequests.getGallery(String.valueOf(perPage), String.valueOf(page));
        isLoad = true;
        call.enqueue(this);
        if (adapter.getItemCount() > 0) {
            adapter.addLoading();
        }
    }

    public void onResponse(Call<Gallery> call, Response<Gallery> response) {
        Log.w("HUMAM-App", response.message());

        if (response.isSuccessful()) {

            Gallery gallery = response.body();
            photoList = gallery.getPhotoesList();
            if (!photoList.isEmpty()) {
                adapter.addItems(photoList);
            }


            Log.w("HUMAM-App", "----------------------------------------------\n");
//            for (int i = 0; i < gallery.getPhotoesList().size(); i++) {
//                Photo photo = gallery.getPhotoesList().get(i);
//                Log.w("HUMAM-App", "URL: " + photo.getUrl());
//            }

        } else {
            Log.w("HUMAM-App failed", response.errorBody().toString());
        }
        adapter.removeLoading();
        swipeRefresh.setRefreshing(false);
        isLoad = false;
    }

    public void onFailure(Call<Gallery> call, Throwable t) {
        Log.w("HUMAM-App onFailure", t.getMessage());
        t.printStackTrace();
        adapter.removeLoading();
        swipeRefresh.setRefreshing(false);
        isLoad = false;

    }

}
