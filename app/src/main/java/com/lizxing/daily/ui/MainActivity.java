package com.lizxing.daily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.ui.English.EnglishFragment;
import com.lizxing.daily.ui.about.AboutFragment;
import com.lizxing.daily.ui.articles.ArticlesFragment;
import com.lizxing.daily.ui.news.NewsFragment;
import com.lizxing.daily.ui.setting.SettingActivity;
import com.lizxing.daily.ui.study.StudyFragment;
import com.lizxing.daily.ui.weather.WeatherActivity;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "===MainActivity";
    private Toolbar toolbar;
    private TextView textView;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private ActionBar actionBar;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment newsFragment;
    private Fragment articlesFragment;
    private Fragment studyFragment;
    private Fragment aboutFragment;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout searchLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
    }



    private void initView(){
        toolbar = findViewById(R.id.toolbar_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        bottomNavigationView =  findViewById(R.id.bottomNavigation);
        searchLayout = findViewById(R.id.layout_search);
        textView = findViewById(R.id.toolbar_title);
    }

    private void initData(){
        //标题栏
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏默认标题
            textView.setText(getResources().getString(R.string.news));
        }

        //抽屉
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //导航栏
        navView.setNavigationItemSelectedListener(OnClickNavItem);

        //默认加载页面
        fragmentManager = getSupportFragmentManager();
        fragments = new ArrayList<>();
        newsFragment = NewsFragment.newInstance();
        fragments.add(newsFragment);
        changeFragment(newsFragment, true);
//        toolbar.setVisibility(View.GONE);

        //底部菜单栏
        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("新闻", getResources().getColor(R.color.White), R.mipmap.ic_news);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("精选", getResources().getColor(R.color.White), R.mipmap.ic_articles);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("学习", getResources().getColor(R.color.White), R.mipmap.ic_reading);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                ("关于", getResources().getColor(R.color.White), R.mipmap.ic_about);
        bottomNavigationView.isColoredBackground(false);
        bottomNavigationView.setItemActiveColorWithoutColoredBackground(getResources().getColor(R.color.Blue));
        bottomNavigationView.disableShadow();
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                selectItem(index);
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
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
                    Intent intent1 = new Intent(MainActivity.this,WeatherActivity.class);
                    startActivity(intent1);
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

    /**
     * 改变当前fragment
     */
    private void changeFragment(Fragment showFragment, boolean add) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (add){
            fragmentTransaction.add(R.id.layout_content, showFragment);
        }
        for (Fragment fragment : fragments) {
            if (showFragment.equals(fragment)) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }

    /**
     * 底部菜单栏
     * 选择显示的item
     */
    private void selectItem(int index){
        switch (index){
            case 0:
                textView.setText(getResources().getString(R.string.news));
                searchLayout.setVisibility(View.VISIBLE);
//                toolbar.setVisibility(View.GONE);
//                StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.White));
//                StatusBarUtil.setStatusFontColor(this,true);
                if (newsFragment == null) {
                    newsFragment = NewsFragment.newInstance();
                    fragments.add(newsFragment);
                    changeFragment(newsFragment, true);
                } else {
                    changeFragment(newsFragment, false);
                }
                break;
            case 1:
                textView.setText(getResources().getString(R.string.articles));
                searchLayout.setVisibility(View.VISIBLE);
//                toolbar.setVisibility(View.GONE);
//                StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.White));
//                StatusBarUtil.setStatusFontColor(this,true);
                if (articlesFragment == null) {
                    articlesFragment = ArticlesFragment.newInstance();
                    fragments.add(articlesFragment);
                    changeFragment(articlesFragment, true);
                } else {
                    changeFragment(articlesFragment, false);
                }
                break;
            case 2:
                textView.setText(getResources().getString(R.string.study));
                searchLayout.setVisibility(View.GONE);
//                toolbar.setVisibility(View.VISIBLE);
//                StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
//                StatusBarUtil.setStatusFontColor(this,false);
                if (studyFragment == null) {
                    studyFragment = StudyFragment.newInstance();
                    fragments.add(studyFragment);
                    changeFragment(studyFragment, true);
                } else {
                    changeFragment(studyFragment, false);
                }
                break;
            case 3:
                textView.setText(getResources().getString(R.string.about));
                searchLayout.setVisibility(View.GONE);
//                toolbar.setVisibility(View.VISIBLE);
//                StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
//                StatusBarUtil.setStatusFontColor(this,false);
                if (aboutFragment == null) {
                    aboutFragment = AboutFragment.newInstance();
                    fragments.add(aboutFragment);
                    changeFragment(aboutFragment, true);
                } else {
                    changeFragment(aboutFragment, false);
                }
                break;
            default:
                break;
        }
    }

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
