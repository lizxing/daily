package com.lizxing.daily.ui.articles;

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
import com.lizxing.daily.items.ArticlesItem;
import com.lizxing.daily.items.NewsItem;
import com.lizxing.daily.ui.news.NewsContentActivity;
import com.lizxing.daily.ui.news.NewsItemAdapter;

import java.util.List;

public class ArticlesItemAdapter extends RecyclerView.Adapter<ArticlesItemAdapter.ViewHolder>{
    private List<ArticlesItem> mItemList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemTitle;
        TextView ItemNickname;
        ImageView ItemPic;
        View ItemView;

        public ViewHolder(View view){
            super(view);
            ItemView = view;
            ItemTitle = view.findViewById(R.id.item_title);
            ItemPic = view.findViewById(R.id.item_pic);
            ItemNickname = view.findViewById(R.id.item_nickname);
        }
    }
    public ArticlesItemAdapter(List<ArticlesItem> itemList, Context context){
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public ArticlesItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item, parent, false);
        final ArticlesItemAdapter.ViewHolder holder = new ArticlesItemAdapter.ViewHolder(view);
        //此处写点击监听
        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ArticlesItem item = mItemList.get(position);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticlesItemAdapter.ViewHolder holder, int position) {
        ArticlesItem item = mItemList.get(position);
        Glide.with(mContext).load(item.getImageUrl()).into(holder.ItemPic);
        holder.ItemTitle.setText(item.getTitle());
        holder.ItemNickname.setText(item.getNickname());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
