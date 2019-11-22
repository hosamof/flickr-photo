package com.flickr.photo.test.helper;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    public static final int PAGE_START = 1;
    /**
     * Set scrolling threshold here
     */
    public static final int PER_PAGE = 20;

    @NonNull
    private LinearLayoutManager layoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     */
    public PaginationListener(@NonNull LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//        Log.w("xxxxxxxxxxxxxx", "visibleItemCount = " + visibleItemCount);
//        Log.w("xxxxxxxxxxxxxx", "totalItemCount = " + totalItemCount);
//        Log.w("xxxxxxxxxxxxxx", "firstVisibleItemPosition" + firstVisibleItemPosition);
//        Log.w("xxxxxxxxxxxxxx", "isLoading = " + isLoading());
//        Log.w("xxxxxxxxxxxxxx", "isLastPage = " + isLastPage());
//        Log.w("xxxxxxxxxxxxxx", "_________________________________________");

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= (PER_PAGE - 1)) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}