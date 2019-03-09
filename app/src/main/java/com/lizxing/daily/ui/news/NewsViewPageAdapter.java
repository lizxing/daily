package com.lizxing.daily.ui.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lizxing.daily.common.ViewPageAdapter;

public class NewsViewPageAdapter extends ViewPageAdapter {

    public NewsViewPageAdapter(FragmentManager fm) {
        super(fm);
        String[] Tabs = {"tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7"};
        setTabs(Tabs);
    }


    /**
     * 创建fragment
     */
    @Override
    public Fragment getItem(int i) {
        return PageFragment.newInstance(i + 1); //谷歌推荐的实例化fragment方法
    }

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
