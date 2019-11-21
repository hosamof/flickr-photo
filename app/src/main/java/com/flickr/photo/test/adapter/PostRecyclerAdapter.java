package com.flickr.photo.test.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flickr.photo.test.R;
import com.flickr.photo.test.helper.BaseViewHolder;
import com.flickr.photo.test.models.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<Photo> mPhotoes;

    public PostRecyclerAdapter(List<Photo> photos) {
        this.mPhotoes = photos;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ViewHolder(view);
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPhotoes.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
//            return  VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoes == null ? 0 : mPhotoes.size();
    }

    public void addItems(List<Photo> postItems) {
        mPhotoes.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        mPhotoes.add(new Photo());
        notifyItemInserted(mPhotoes.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPhotoes.size() - 1;
        Photo item = getItem(position);
        if (item != null) {
            mPhotoes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mPhotoes.clear();
        notifyDataSetChanged();
    }

    public Photo getItem(int position) {
        return mPhotoes.get(position);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            Photo item = mPhotoes.get(position);
            Picasso.get().load(item.getUrl()).fit().centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageView);
        }
    }

    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
        }
    }
}
