package com.lizxing.daily.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.common.Item;
import com.lizxing.daily.gson.News;
import com.lizxing.daily.gson.NewsList;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsPageFragment extends DailyFragment {

    private static final int NEWS_WORLD = 1; //国际
    private static final int NEWS_HOME = 2; //国内
    private static final int NEWS_SOCIAL = 3; //社会
    private static final int NEWS_AI = 4; //人工智能
    private static final int NEWS_IT = 5; //IT资讯
    private static final int NEWS_VR = 6; //VR科技
    private static final int NEWS_MOBILE = 7; //移动
    private static final int NEWS_ANECDOTE = 8; //奇闻
    private static final int NEWS_HEALTH = 9; //健康
    private static final int NEWS_TRAVEL = 10; //旅游
    private static final int NEWS_SPORT = 11; //体育


    private static final String TAG = "NewsPageFragment";
    public static final String PAGE = "PAGE";
    private int mPage;
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<Item> itemList = new ArrayList<Item>();
    private List<Integer> requestedList = new ArrayList<>();


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

        initData();
        initView(view);
        
        return view;
    }
    
    private void initData(){
        if(requestedList.indexOf(mPage) != -1){
            //若本页已请求过数据，返回
            return;
        }
        if(mPage == 1){
            Log.d(TAG, "initData: 请求数据,页面："+mPage);
            requestNews();
            if(requestedList.indexOf(mPage) == -1) {
                requestedList.add(mPage);  //本页加入已请求列表
            }
        }
    }
    
    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        newsItemAdapter = new NewsItemAdapter(itemList);
//        recyclerView.setAdapter(newsItemAdapter);
    }

    private void requestNews(){
//        Item item = new Item("1","2","3","4");
//        itemList.add(item);
        String newsAddress = getAddress(mPage);
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsList newlist = Utility.handleNewsResponse(responseText);
                final int code = newlist.code;
                final String msg = newlist.msg;
                if (code == 200){
                    itemList.clear();
                    for (News news:newlist.newsList){
                        Item item = new Item(news.title,news.description,news.picUrl, news.url);
                        itemList.add(item);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsItemAdapter = new NewsItemAdapter(itemList);
                            recyclerView.setAdapter(newsItemAdapter);
                        };
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "返回数据错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "新闻请求响应失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String getAddress(int mPage){
        String address = "http://api.tianapi.com/world/?key=6634dbe82d9bddbf27123652cff14e0b";
        switch(mPage){
            case NEWS_WORLD:
                break;
            case NEWS_HOME:
                address = address.replace("world","guonei");
                break;
            case NEWS_SOCIAL:
                address = address.replace("world","social");
                break;
            case NEWS_AI:
                address = address.replace("world","ai");
                break;
            case NEWS_IT:
                address = address.replace("world","it");
                break;
            case NEWS_VR:
                address = address.replace("world","vr");
                break;
            case NEWS_MOBILE:
                address = address.replace("world","mobile");
                break;
            case NEWS_ANECDOTE:
                address = address.replace("world","qiwen");
                break;
            case NEWS_HEALTH:
                address = address.replace("world","health");
                break;
            case NEWS_TRAVEL:
                address = address.replace("world","travel");
                break;
            case NEWS_SPORT:
                address = address.replace("world","tiyu");
                break;
            default:
        }
        return address;
    }
}
