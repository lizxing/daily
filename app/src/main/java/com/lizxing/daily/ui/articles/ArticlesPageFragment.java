package com.lizxing.daily.ui.articles;

import android.content.Context;
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
import com.lizxing.daily.gson.Articles;
import com.lizxing.daily.gson.ArticlesList;
import com.lizxing.daily.items.ArticlesItem;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

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

        initData();
        initView(view);

        return view;
    }

    private void initData(){
        Log.d(TAG, "initData: 请求数据,页面："+mPage);
        requestArticles();
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_articles);
        //设置列数为2
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 发起请求获取文章
     */
    private void requestArticles(){
        String newsAddress = "http://api.tianapi.com/weixin/home/?key=6634dbe82d9bddbf27123652cff14e0b&src=txd30ffae8";
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final ArticlesList articlesList = Utility.handleArticlesResponse(responseText);
                final int code = articlesList.code;
                final String msg = articlesList.msg;
                if (code == 200){
                    itemList.clear();
                    for (Articles articles:articlesList.articlesList){
                        ArticlesItem item = new ArticlesItem(articles.title,articles.description,articles.picUrl, articles.url, articles.nickname);
                        itemList.add(item);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            articlesItemAdapter = new ArticlesItemAdapter(itemList, getContext());
                            recyclerView.setAdapter(articlesItemAdapter);
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
                        Toast.makeText(getActivity().getApplicationContext(), "文章请求响应失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
