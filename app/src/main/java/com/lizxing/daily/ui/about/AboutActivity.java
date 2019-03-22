package com.lizxing.daily.ui.about;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;

import com.lizxing.daily.R;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    private NestedScrollView nestedScrollView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        intView();
    }

    private void intView(){
        nestedScrollView = findViewById(R.id.nestedscrollview);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.img_daily_black)//图片
                .setDescription("每日，看看新闻看看天气")//介绍
                .addGroup("应用简介")
                .addItem(new Element().setTitle("版本号 1.0"))
                .addItem(new Element().setTitle("  每日是一个日常功能APP，主要方便用户在碎片时间看看天气，看看新闻，后续功能待开发。。。"))
                .addGroup("数据来源")
                .addItem(new Element().setTitle("和风天气"))
                .addItem(new Element().setTitle("天行数据"))
                .addGroup("联系")
                .addEmail("1483588183@qq.com")//邮箱
//                .addWebsite("http://zhaoweihao.me")//网站
//                .addPlayStore("com.example.abouttest")//应用商店
                .addGitHub("lizxing")//github
                .create();

        nestedScrollView.addView(aboutPage);
    }
}
