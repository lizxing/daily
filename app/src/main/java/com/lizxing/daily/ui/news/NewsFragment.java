package com.lizxing.daily.ui.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.utils.StatusBarUtil;

public class NewsFragment extends DailyFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsViewPageAdapter newsViewPageAdapter;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initView(view);
        initData();

        return view;
    }

    private void initView(View view){
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager_news);
    }

    private void initData(){
        //适配器
        newsViewPageAdapter = new NewsViewPageAdapter(getFragmentManager());
        viewPager.setOffscreenPageLimit(3);//懒加载左右各3页

        //加载新闻相关
        viewPager.setAdapter(newsViewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


}
