package com.lizxing.daily.ui.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.lizxing.daily.common.ViewPageAdapter;

public class NewsViewPageAdapter extends ViewPageAdapter {

    public NewsViewPageAdapter(FragmentManager fm) {
        super(fm);
        String[] Tabs = {"国际", "国内", "社会", "人工智能", "IT资讯", "VR科技", "移动互联", "奇闻", "健康", "旅游", "体育"};
        setTabs(Tabs);
    }

    /**
     * 创建fragment
     */
    @Override
    public Fragment getItem(int i) {
        return NewsPageFragment.newInstance(i + 1); //谷歌推荐的实例化fragment方法
    }

    /**
     * 获取tabs数量
     */
    @Override
    public int getCount() {
        return Tabs.length;
    }

    /**
     * 重新添加tabs
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return Tabs[position];
    }
}
