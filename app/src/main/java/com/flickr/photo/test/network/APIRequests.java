package com.flickr.photo.test.network;

import com.flickr.photo.test.constants.Urls;
import com.flickr.photo.test.models.Gallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Humam Alayad
 * <p>
 * This is the REST API for Retrofit via interface
 */
public interface APIRequests {
    @GET(Urls.RECENT_PHOTOES_URL)
    Call<Gallery> getGallery(@Query("per_page") String perPage, @Query("page") String page);
}
