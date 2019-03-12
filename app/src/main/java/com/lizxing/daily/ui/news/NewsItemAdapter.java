package com.lizxing.daily.ui.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizxing.daily.R;
import com.lizxing.daily.common.Item;

import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private List<Item> mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemText;
        TextView ItemDescr;
        ImageView ItemPic;

        public ViewHolder(View view){
            super(view);
            ItemText = view.findViewById(R.id.item_text);
            ItemPic = view.findViewById(R.id.item_pic);
            ItemDescr = view.findViewById(R.id.descr_text);
        }
    }
    public NewsItemAdapter(List<Item> itemList){
        mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //此处写点击监听
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mItemList.get(position);
        //Glide.with(getContext()).load(item.getImageUrl()).into(holder.ItemPic);
        holder.ItemText.setText(item.getTitle());
        holder.ItemDescr.setText(item.getDescr());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}
