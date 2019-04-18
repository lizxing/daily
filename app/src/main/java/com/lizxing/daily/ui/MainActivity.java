package com.lizxing.daily.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.database.MyDatabaseHelper;
import com.lizxing.daily.ui.about.AboutActivity;
import com.lizxing.daily.ui.articles.ArticlesPageFragment;
import com.lizxing.daily.ui.articles.ArticlesViewPageAdapter;
import com.lizxing.daily.ui.news.NewsPageFragment;
import com.lizxing.daily.ui.news.NewsViewPageAdapter;
import com.lizxing.daily.ui.English.EnglishActivity;
import com.lizxing.daily.ui.setting.SettingActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;
import com.lizxing.daily.utils.StatusBarUtil;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "===MainActivity";
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NewsViewPageAdapter newsViewPageAdapter;
    private ArticlesViewPageAdapter articlesViewPageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
    }

    private void initData(){
        //标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setTitle(getResources().getString(R.string.news));
        }

        //导航栏
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(OnClickNavItem);

        //适配器
        newsViewPageAdapter = new NewsViewPageAdapter(getSupportFragmentManager());
        articlesViewPageAdapter = new ArticlesViewPageAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);//懒加载左右各3页

        //默认加载新闻相关
        viewPager.setAdapter(newsViewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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
            case  R.id.search:
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
            Fragment current = getSupportFragmentManager().findFragmentById(R.id.viewpager);
            switch (menuItem.getItemId()){
                case R.id.nav_English:
                    Intent intent = new Intent(MainActivity.this,EnglishActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_weather:
                    Intent intent1 = new Intent(MainActivity.this,WeatherActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_news:
                    if(current instanceof ArticlesPageFragment){
                        //加载文章页面
                        viewPager.setAdapter(newsViewPageAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(getResources().getString(R.string.news));
                    }
                    break;
                case R.id.nav_articles:
                    if(current instanceof NewsPageFragment){
                        //加载文章页面
                        viewPager.setAdapter(articlesViewPageAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(getResources().getString(R.string.articles));
                    }
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

    // 用来计算返回键的点击间隔时间
     private long exitTime = 0;
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getAction() == KeyEvent.ACTION_DOWN) {
             if ((System.currentTimeMillis() - exitTime) > 2000) {
                 //弹出提示，可以有多种方式
                 Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                 exitTime = System.currentTimeMillis();
             } else {
                 finish();
             }
             return true;
         }

         return super.onKeyDown(keyCode, event);
     }



}
