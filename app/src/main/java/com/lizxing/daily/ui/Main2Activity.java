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
import android.view.Menu;
import android.view.MenuItem;

import com.lizxing.daily.R;
import com.lizxing.daily.ui.English.EnglishActivity;
import com.lizxing.daily.ui.English.EnglishFragment;
import com.lizxing.daily.ui.about.AboutActivity;
import com.lizxing.daily.ui.about.AboutFragment;
import com.lizxing.daily.ui.articles.ArticlesFragment;
import com.lizxing.daily.ui.news.NewsFragment;
import com.lizxing.daily.ui.setting.SettingActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        initView();
        initData();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        bottomNavigationView =  findViewById(R.id.bottomNavigation);
    }

    private void initData(){
        //标题栏
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setTitle(getResources().getString(R.string.news));
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
        //bottomNavigationView.disableShadow();
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
                    Intent intent = new Intent(Main2Activity.this,EnglishActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_weather:
                    Intent intent1 = new Intent(Main2Activity.this,WeatherActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_news:

                    break;
                case R.id.nav_articles:

                    break;
                case R.id.nav_about:
                    Intent intent2 = new Intent(Main2Activity.this,AboutActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_setting:
                    Intent intent3 = new Intent(Main2Activity.this,SettingActivity.class);
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
                actionBar.setTitle(getResources().getString(R.string.news));
                if (newsFragment == null) {
                    newsFragment = NewsFragment.newInstance();
                    fragments.add(newsFragment);
                    changeFragment(newsFragment, true);
                } else {
                    changeFragment(newsFragment, false);
                }
                break;
            case 1:
                actionBar.setTitle(getResources().getString(R.string.articles));
                if (articlesFragment == null) {
                    articlesFragment = ArticlesFragment.newInstance();
                    fragments.add(articlesFragment);
                    changeFragment(articlesFragment, true);
                } else {
                    changeFragment(articlesFragment, false);
                }
                break;
            case 2:
                actionBar.setTitle(getResources().getString(R.string.study));
                if (studyFragment == null) {
                    studyFragment = EnglishFragment.newInstance();
                    fragments.add(studyFragment);
                    changeFragment(studyFragment, true);
                } else {
                    changeFragment(studyFragment, false);
                }
                break;
            case 3:
                actionBar.setTitle(getResources().getString(R.string.about));
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
}
