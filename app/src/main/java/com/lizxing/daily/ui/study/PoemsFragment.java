package com.lizxing.daily.ui.study;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.gson.Poems;
import com.lizxing.daily.gson.PoemsList;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PoemsFragment extends DailyFragment {

    private Toolbar toolbar;
    private TextView titleText;
    private TextView contentText;
    private TextView introText;
    private TextView authotText;

    public static PoemsFragment newInstance() {
        PoemsFragment fragment = new PoemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poems, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        //标题栏
        toolbar = view.findViewById(R.id.toolbar_poems);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(PoemsFragment.this);
                fragmentTransaction.commit();
            }
        });

        titleText = view.findViewById(R.id.poem_title);
        contentText = view.findViewById(R.id.poem_content);
        introText = view.findViewById(R.id.poem_intro);
        authotText = view.findViewById(R.id.poem_author);

        //刷新相关
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能

        refreshLayout.setPrimaryColors(getResources().getColor(R.color.colorPrimary));//主题颜色
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestPoems();
                refreshlayout.finishRefresh(3000/*,false*/);//传入false表示刷新失败
            }
        });
    }

    private void initData(){
        requestPoems();
    }

    /**
     * 发起请求获取诗词
     */
    private void requestPoems(){
        String newsAddress = "http://api.tianapi.com/txapi/poetry/?key=6634dbe82d9bddbf27123652cff14e0b&num=1";
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final PoemsList poemsList = Utility.handlePoemsResponse(responseText);
                final int code = poemsList.code;
                final String msg = poemsList.msg;
                if (code == 200){
                    Poems poems = poemsList.poemsList.get(0);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleText.setText(poems.title);
                            authotText.setText(poems.author);
                            contentText.setText(poems.content);
                            introText.setText(poems.intro);
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "返回数据错误"+code,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
