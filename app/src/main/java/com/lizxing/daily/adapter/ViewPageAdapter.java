package com.lizxing.daily.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.telecom.Call;

import com.lizxing.daily.ui.news.PageFragment;

import java.net.ContentHandler;

public class ViewPageAdapter extends FragmentPagerAdapter {

    private static  final String[] Tabs = {"tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7"};

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return PageFragment.newInstance(i + 1); //创建fragment
    }

    @Override
    public int getCount() {
        return Tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Tabs[position];
    } //设置tabs
}
