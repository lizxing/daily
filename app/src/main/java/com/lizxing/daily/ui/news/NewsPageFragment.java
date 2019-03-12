package com.lizxing.daily.ui.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.common.Item;

import java.util.ArrayList;
import java.util.List;

public class NewsPageFragment extends DailyFragment {

    public static final String PAGE = "PAGE";
    private int mPage;
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<Item> itemList = new ArrayList<Item>();

    public static NewsPageFragment newInstance(int page) {
        NewsPageFragment pagefragment = new NewsPageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        pagefragment.setArguments(args);
        return pagefragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(PAGE); //获取传入的值
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_news, container, false);

        if(itemList !=null){
            itemList.clear();
        }
        Item item = new Item("1","2","3","4");
        itemList.add(item);
        recyclerView = view.findViewById(R.id.recyclerView_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        newsItemAdapter = new NewsItemAdapter(itemList);
        recyclerView.setAdapter(newsItemAdapter);

        return view;
    }

}
