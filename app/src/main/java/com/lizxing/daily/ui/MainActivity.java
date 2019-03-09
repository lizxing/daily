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
import com.lizxing.daily.adapter.ViewPageAdapter;
import com.lizxing.daily.ui.weather.WeatherActivity;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPageAdapter viewPageAdapter;
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

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPageAdapter);
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
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

}
