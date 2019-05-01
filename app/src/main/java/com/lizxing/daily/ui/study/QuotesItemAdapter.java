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
import com.lizxing.daily.items.QuotesItem;
import com.lizxing.daily.ui.articles.ArticlesItemAdapter;

import java.util.List;

public class QuotesItemAdapter extends RecyclerView.Adapter<QuotesItemAdapter.ViewHolder>{

    private List<QuotesItem> mItemList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemContent;
        TextView ItemAuthor;
        View ItemView;

        public ViewHolder(View view){
            super(view);
            ItemView = view;
            ItemContent = view.findViewById(R.id.quotes_content);
            ItemAuthor = view.findViewById(R.id.quotes_author);
        }
    }
    public QuotesItemAdapter(List<QuotesItem> itemList, Context context){
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public QuotesItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotes_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(QuotesItemAdapter.ViewHolder holder, int position) {
        QuotesItem item = mItemList.get(position);
        holder.ItemContent.setText(item.getContent());
        holder.ItemAuthor.setText("——"+item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}
