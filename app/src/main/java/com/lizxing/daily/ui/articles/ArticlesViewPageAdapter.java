package com.lizxing.daily.ui.articles;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lizxing.daily.common.ViewPageAdapter;

public class ArticlesViewPageAdapter extends ViewPageAdapter {

    public ArticlesViewPageAdapter(FragmentManager fm) {
        super(fm);
        String[] Tabs = {"精选", "收藏"};
        setTabs(Tabs);
    }

    /**
     * 创建fragment
     */
    @Override
    public Fragment getItem(int i) {
        return ArticlesPageFragment.newInstance("1","2"); //谷歌推荐的实例化fragment方法
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
