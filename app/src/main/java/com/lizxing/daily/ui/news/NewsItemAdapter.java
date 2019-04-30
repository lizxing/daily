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
import com.lizxing.daily.common.WebContentActivity;
import com.lizxing.daily.items.NewsItem;

import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private List<NewsItem> mItemList;
    private Context mContext;
    private int ITEM_TYPE_NORMAL = 0;   //普通item
    private int ITEM_TYPE_HEADER = 1;   //头item
    private View mHeaderView;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemText;
        TextView ItemDescr;
        ImageView ItemPic;
        View ItemView;

        public ViewHolder(View view, int type){
            super(view);
            if(type == 1){
                return;
            }
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
        if (viewType == ITEM_TYPE_HEADER) {
            //头item
            return new ViewHolder(mHeaderView, ITEM_TYPE_HEADER);
        }else {
            //普通item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view, ITEM_TYPE_NORMAL);
            //此处写点击监听
            holder.ItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    NewsItem item = mItemList.get(position-1);
                    Intent intent = new Intent(mContext, WebContentActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("uri", item.getUri());
                    intent.putExtra("type", "News");
                    mContext.startActivity(intent);
                }
            });
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER) {
            return;
        }
        NewsItem item;
        if(mHeaderView != null){
            //保证第一个item绑定的是mItenList的第一条数据（例如当前position=2,应当绑定itemList的get(1)）
            item = mItemList.get(position-1);
        }else {
            item = mItemList.get(position);
        }
        Glide.with(mContext).load(item.getImageUrl()).into(holder.ItemPic);
        holder.ItemText.setText(item.getTitle());
        holder.ItemDescr.setText(item.getDescr());
    }

    @Override
    public int getItemCount() {
        int itemCount = mItemList.size();
        if (mHeaderView != null) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return super.getItemViewType(position); //恒为0
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }
}
