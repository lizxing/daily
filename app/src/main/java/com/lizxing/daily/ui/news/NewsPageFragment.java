package com.lizxing.daily.ui.news;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.database.MyDatabaseHelper;
import com.lizxing.daily.items.NewsItem;
import com.lizxing.daily.common.RecycleViewDivider;
import com.lizxing.daily.gson.News;
import com.lizxing.daily.gson.NewsList;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
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


    private static final String TAG = "======NewsPageFragment";
    public static final String PAGE = "PAGE";
    private int mPage;
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<NewsItem> itemList = new ArrayList<NewsItem>();
    private MyDatabaseHelper databaseHelper;


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


        initView(view);
        initData();
        return view;
    }
    
    private void initData(){
        Log.d(TAG, "initData: 请求数据,页面："+mPage);
        //创建数据库
        databaseHelper = new MyDatabaseHelper(getContext(), "News.db", null, 1);
        //requestNews();
        getNews();
    }
    
    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        //recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.Gainsboro)));

        //刷新相关
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setPrimaryColors(getResources().getColor(R.color.colorPrimary));//主题颜色
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestNews();
                getNews();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    /**
     * 发起请求获取新闻
     * 数据插入数据库
     */
    private void requestNews(){
        String newsAddress = getAddress(mPage);
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //获取到的数据
                final NewsList newlist = Utility.handleNewsResponse(responseText);
                final int code = newlist.code;
                final String msg = newlist.msg;
                //数据库相关
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                if (code == 200){
                    for (News news:newlist.newsList){
                        //添加数据到数据库
                        values.put("time", news.time);
                        values.put("title", news.title);
                        values.put("description", news.description);
                        values.put("picUrl", news.picUrl);
                        values.put("url", news.url);
                        values.put("type", mPage);
                        db.insert("News", null, values);
                        values.clear();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getNews();
                        }
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

    /**
     * 获取请求地址
     */
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

    /**
     * 查询数据库
     * 获取新闻
     */
    private void getNews(){
        itemList.clear();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from News where type = ?",new String[]{String.valueOf(mPage)});
//        Cursor cursor = db.query("News",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String picUrl = cursor.getString(cursor.getColumnIndex("picUrl"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                NewsItem item = new NewsItem(title, description, picUrl, url);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        Log.d(TAG, "setAdapter itemList="+itemList);
        newsItemAdapter = new NewsItemAdapter(itemList, getContext());
        recyclerView.setAdapter(newsItemAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
