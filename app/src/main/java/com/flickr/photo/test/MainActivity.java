package com.flickr.photo.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.flickr.photo.test.adapter.PostRecyclerAdapter;
import com.flickr.photo.test.controllers.GalleryController;
import com.flickr.photo.test.helper.PaginationListener;
import com.flickr.photo.test.helper.RecyclerItemClickListener;
import com.flickr.photo.test.models.Photo;
import com.flickr.photo.test.ui.fragments.FullScreenFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.flickr.photo.test.helper.PaginationListener.PAGE_START;
import static com.flickr.photo.test.helper.PaginationListener.PER_PAGE;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private PostRecyclerAdapter adapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false; // inifinty
    int itemCount = 0;
    GalleryController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // setmRecyclerView click listener
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Photo p = adapter.getItem(position);
                        String url = p != null && p.getUrl() != null ? p.getUrl() : "";
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_page, FullScreenFragment.newInstance(url))
                                    .addToBackStack("full_screen")
                                    .commit();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        adapter = new PostRecyclerAdapter(new ArrayList<Photo>());
        mRecyclerView.setAdapter(adapter);
        controller = new GalleryController(adapter, swipeRefresh);
        doApiCall();

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                currentPage++;
                doApiCall();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return controller.isLoading();
            }
        });
    }

    /**
     * do api call here to fetch data from server*
     */
    private void doApiCall() {
        controller.getNewElements(PER_PAGE, currentPage);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        adapter.clear();
        doApiCall();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("full_screen") != null) {
            getSupportFragmentManager().popBackStackImmediate("full_screen", 0);
        } else {
            super.onBackPressed();
        }
    }
}
