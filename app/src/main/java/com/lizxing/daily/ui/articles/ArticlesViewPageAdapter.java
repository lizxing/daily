package com.lizxing.daily.ui.articles;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.lizxing.daily.common.ViewPageAdapter;

public class ArticlesViewPageAdapter extends ViewPageAdapter {

    public ArticlesViewPageAdapter(FragmentManager fm) {
        super(fm);
        String[] Tabs = {"公众号精选", "人民日报"};
        setTabs(Tabs);
    }

    /**
     * 创建fragment
     */
    @Override
    public Fragment getItem(int i) {
        return ArticlesPageFragment.newInstance(i + 1); //谷歌推荐的实例化fragment方法
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
