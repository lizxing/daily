package com.lizxing.daily.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends DailyFragment {

    private FrameLayout frameLayout;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        frameLayout = view.findViewById(R.id.layout_about);

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.img_daily_black)//图片
                .setDescription("每日，管好你的碎片时间")//介绍
                .addGroup("应用简介")
                .addItem(new Element().setTitle("\t版本号 1.0"))
                .addItem(new Element().setTitle("\t通过每日APP，你可以：\n\t1、学习英语\n\t2、了解天气\n\t3、看看新闻\n\t4、读读文章\n\t后续功能待开发。。。"))
                .addGroup("数据来源")
                .addItem(new Element().setTitle("\t必应"))
                .addItem(new Element().setTitle("\t和风天气"))
                .addItem(new Element().setTitle("\t天行数据"))
                .addItem(new Element().setTitle("\t微信平台"))
                .addGroup("技术参考")
                .addItem(new Element().setTitle("\t简书"))
                .addItem(new Element().setTitle("\tCSDN博客"))
                .addItem(new Element().setTitle("\tGitHub"))
                .addItem(new Element().setTitle("\t等等"))
                .addGroup("联系")
                .addEmail("1483588183@qq.com")//邮箱
//                .addWebsite("http://zhaoweihao.me")//网站
//                .addPlayStore("com.example.abouttest")//应用商店
                .addGitHub("lizxing")//github
                .create();

        frameLayout.addView(aboutPage);

        return view;
    }
}
