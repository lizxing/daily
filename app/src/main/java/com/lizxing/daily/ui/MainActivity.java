package com.lizxing.daily.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lizxing.daily.R;
import com.lizxing.daily.common.ViewPageAdapter;
import com.lizxing.daily.ui.about.AboutActivity;
import com.lizxing.daily.ui.news.NewsViewPageAdapter;
import com.lizxing.daily.ui.setting.SettingActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NewsViewPageAdapter newsViewPageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(OnClickNavItem);

        //加载新闻相关
        newsViewPageAdapter = new NewsViewPageAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(newsViewPageAdapter);
        viewPager.setOffscreenPageLimit(3);//懒加载左右各3页
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 处理点击标题栏子项
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    /**
     * 处理点击导航栏子项
     */
    private NavigationView.OnNavigationItemSelectedListener OnClickNavItem = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_weather:
                    Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_about:
                    Intent intent2 = new Intent(MainActivity.this,AboutActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_setting:
                    Intent intent3 = new Intent(MainActivity.this,SettingActivity.class);
                    startActivity(intent3);
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

}
