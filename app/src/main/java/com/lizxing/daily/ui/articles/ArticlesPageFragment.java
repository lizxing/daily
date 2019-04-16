package com.lizxing.daily.ui.articles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.database.MyDatabaseHelper;
import com.lizxing.daily.gson.Articles;
import com.lizxing.daily.gson.ArticlesList;
import com.lizxing.daily.items.ArticlesItem;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ArticlesPageFragment extends Fragment {

    private static final String TAG = "ArticlesPageFragment";
    public static final String PAGE = "PAGE";
    private int mPage;
    private RecyclerView recyclerView;
    private List<ArticlesItem> itemList = new ArrayList<ArticlesItem>();
    private ArticlesItemAdapter articlesItemAdapter;
    private MyDatabaseHelper databaseHelper;

    public static ArticlesPageFragment newInstance(int page) {
        ArticlesPageFragment pagefragment = new ArticlesPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_articles_page, container, false);

        initView(view);
        initData();

        return view;
    }

    private void initData(){
        Log.d(TAG, "initData: 请求数据,页面："+mPage);
        //创建数据库
        databaseHelper = new MyDatabaseHelper(getContext(), "Articles.db", null, 1);
        //获取内容
        requestArticles();
        //适配器
        articlesItemAdapter = new ArticlesItemAdapter(itemList, getContext());
        recyclerView.setAdapter(articlesItemAdapter);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_articles);
        //设置列数为2
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        //刷新相关
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setPrimaryColors(getResources().getColor(R.color.colorPrimary));//主题颜色
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshArticles();
                refreshlayout.finishRefresh(3000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                requestArticles();
                refreshlayout.finishLoadMore(3000/*,false*/);//传入false表示加载失败
            }
        });
    }

    /**
     * 刷新文章
     * 删除数据库内容
     * 重新请求
     */
    private void refreshArticles(){
        //删除旧数据
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("Articles",null,null);
        itemList.clear();
        if(articlesItemAdapter != null){
            articlesItemAdapter.notifyDataSetChanged();
        }
        requestArticles();
    }

    /**
     * 发起请求获取文章
     */
    private void requestArticles(){
        String newsAddress = "http://api.tianapi.com/wxnew/?key=6634dbe82d9bddbf27123652cff14e0b&num=20";
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //获取到的数据
                final ArticlesList articlesList = Utility.handleArticlesResponse(responseText);
                final int code = articlesList.code;
                final String msg = articlesList.msg;
                //数据库相关
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                if (code == 200){
                    for (Articles articles:articlesList.articlesList){
                        ArticlesItem item = new ArticlesItem(articles.title,articles.description,articles.picUrl, articles.url);
                        itemList.add(item);
                        //添加新数据到数据库
                        values.put("time", articles.time);
                        values.put("title", articles.title);
                        values.put("description", articles.description);
                        values.put("picUrl", articles.picUrl);
                        values.put("url", articles.url);
                        db.insert("Articles", null, values);
                        values.clear();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getArticles();
                        };
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getArticles();
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
                        getArticles();
                        Toast.makeText(getActivity().getApplicationContext(), "文章请求响应失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * 查询数据库
     * 获取文章
     */
    private void getArticles(){
        int len1 = itemList.size();
        int len2 = 0;
        itemList.clear();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("Articles",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String picUrl = cursor.getString(cursor.getColumnIndex("picUrl"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                ArticlesItem item = new ArticlesItem(title, description, picUrl, url);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        len2 = itemList.size();
        if(len1 == len2){
            Toast.makeText(getActivity().getApplicationContext(), "无新数据", Toast.LENGTH_SHORT).show();
        }else if(len2 > len1){
            Toast.makeText(getActivity().getApplicationContext(), "已更新"+(len2-len1)+"条数据", Toast.LENGTH_SHORT).show();
        }
        if(articlesItemAdapter != null){
            articlesItemAdapter.notifyDataSetChanged();
        }
    }
}
