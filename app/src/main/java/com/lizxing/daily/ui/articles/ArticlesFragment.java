package com.lizxing.daily.ui.articles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.utils.StatusBarUtil;

public class ArticlesFragment extends DailyFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArticlesViewPageAdapter articlesViewPageAdapter;

    public static ArticlesFragment newInstance() {
        ArticlesFragment fragment = new ArticlesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        initView(view);
        initData();

        return view;
    }

    private void initView(View view){
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager_articles);
    }

    private void initData(){
        //适配器
        articlesViewPageAdapter = new ArticlesViewPageAdapter(getFragmentManager());

        //加载文章相关
        viewPager.setAdapter(articlesViewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
