/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.vimal.recyclerviewpaginationloader.Api.model.Source;
import com.vimal.recyclerviewpaginationloader.R;

import java.util.List;


public class CategoryDataPagination extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;
    static Context context;
    List<Source> dataSet;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    int selectpos = -1;

    public CategoryDataPagination(Context context, List<Source> data) {
        this.context = context;
        this.dataSet = data;
        selectpos = -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.category_data_adapter, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_MOVIE) {


            Glide.with(context).load(dataSet.get(position).getSourceThumbPath()).placeholder(R.drawable.couple).into(((MovieHolder) holder).image);
            ((MovieHolder) holder).textView.setText(dataSet.get(position).getSourceName());

            ((MovieHolder) holder).mainlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, WallPaperDisplayActivity.class);
//                    intent.putExtra("position", position);
//                    intent.putExtra("imagelist", dataSet);
//                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position).getStatus() == null)
            return TYPE_MOVIE;
        else if (dataSet.get(position).getStatus().equals("load")) {
            return TYPE_LOAD;
        } else {
            return TYPE_MOVIE;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView mainlay;
        ImageView image;

        public MovieHolder(View itemView) {
            super(itemView);

            mainlay = itemView.findViewById(R.id.mainlay);
            textView = itemView.findViewById(R.id.textview);
            image = itemView.findViewById(R.id.image);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


}
