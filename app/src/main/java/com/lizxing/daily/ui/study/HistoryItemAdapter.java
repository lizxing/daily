package com.lizxing.daily.ui.study;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lizxing.daily.R;
import com.lizxing.daily.common.WebContentActivity;
import com.lizxing.daily.gson.History;
import com.lizxing.daily.items.ArticlesItem;
import com.lizxing.daily.items.HistoryItem;
import com.lizxing.daily.ui.articles.ArticlesItemAdapter;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>{

    private List<HistoryItem> mItemList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemDate;
        TextView ItemTitle;
        View ItemView;

        public ViewHolder(View view){
            super(view);
            ItemView = view;
            ItemDate = view.findViewById(R.id.history_date);
            ItemTitle = view.findViewById(R.id.history_title);
        }
    }
    public HistoryItemAdapter(List<HistoryItem> itemList, Context context){
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public HistoryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryItemAdapter.ViewHolder holder, int position) {
        HistoryItem item = mItemList.get(position);
        holder.ItemDate.setText(item.getDate());
        holder.ItemTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}
