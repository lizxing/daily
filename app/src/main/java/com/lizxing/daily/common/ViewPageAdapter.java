package com.lizxing.daily.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPageAdapter extends FragmentPagerAdapter {

    protected String[] Tabs = {};

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    protected void setTabs(String[] tabs){
        this.Tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }
    @Override
    public int getCount() {
        return 0;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
