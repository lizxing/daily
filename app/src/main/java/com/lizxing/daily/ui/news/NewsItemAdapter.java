package com.lizxing.daily.ui.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lizxing.daily.R;
import com.lizxing.daily.items.NewsItem;

import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private List<NewsItem> mItemList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemText;
        TextView ItemDescr;
        ImageView ItemPic;
        View ItemView;

        public ViewHolder(View view){
            super(view);
            ItemView = view;
            ItemText = view.findViewById(R.id.item_text);
            ItemPic = view.findViewById(R.id.item_pic);
            ItemDescr = view.findViewById(R.id.descr_text);
        }
    }
    public NewsItemAdapter(List<NewsItem> itemList, Context context){
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //此处写点击监听
        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                NewsItem item = mItemList.get(position);
                Intent intent = new Intent(mContext, NewsContentActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("uri", item.getUri());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem item = mItemList.get(position);
        Glide.with(mContext).load(item.getImageUrl()).into(holder.ItemPic);
        holder.ItemText.setText(item.getTitle());
        holder.ItemDescr.setText(item.getDescr());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}
