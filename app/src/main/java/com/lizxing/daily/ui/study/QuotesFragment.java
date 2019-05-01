package com.lizxing.daily.ui.study;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.gson.Quotes;
import com.lizxing.daily.gson.QuotesList;
import com.lizxing.daily.items.QuotesItem;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QuotesFragment extends DailyFragment {

    private Toolbar toolbar;
    private QuotesItemAdapter quotesItemAdapter;
    private RecyclerView recyclerView;
    private List<QuotesItem> itemList = new ArrayList<QuotesItem>();

    public static QuotesFragment newInstance() {
        QuotesFragment fragment = new QuotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_quotes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //标题栏
        toolbar = view.findViewById(R.id.toolbar_quotes);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(QuotesFragment.this);
                fragmentTransaction.commit();
            }
        });
    }

    private void initData(){
        requestQuotes();
    }

    /**
     * 发起请求获取历史
     */
    private void requestQuotes(){
        String quotesAddress = getAddress();
        HttpUtil.sendOkHttpRequest(quotesAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //获取到的数据
                final QuotesList quotesList = Utility.handleQuotesResponse(responseText);
                final int code = quotesList.code;
                final String msg = quotesList.msg;


                if (code == 200){
                    for (Quotes quotes:quotesList.quotesList){
                        QuotesItem item = new QuotesItem(quotes.content,quotes.author);
                        itemList.add(item);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //适配器
                            quotesItemAdapter = new QuotesItemAdapter(itemList, getContext());
                            recyclerView.setAdapter(quotesItemAdapter);
                            quotesItemAdapter.notifyDataSetChanged();
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
                        Toast.makeText(getActivity().getApplicationContext(), "名言请求响应失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 获取请求地址
     */
    private String getAddress(){
        String address = "http://api.tianapi.com/txapi/dictum/?key=6634dbe82d9bddbf27123652cff14e0b&num=10";
        return address;
    }
}
