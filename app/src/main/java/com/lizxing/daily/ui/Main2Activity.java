package com.lizxing.daily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.View;
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
import com.lizxing.daily.ui.about.AboutActivity;
import com.lizxing.daily.ui.articles.ArticlesPageFragment;
import com.lizxing.daily.ui.news.NewsPageFragment;
import com.lizxing.daily.ui.setting.SettingActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;

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
    }

    private void initData(){
        //标题栏
        setSupportActionBar(toolbar);

        //抽屉
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //导航栏
        navView.setNavigationItemSelectedListener(OnClickNavItem);

        //加载页面


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
}
