package com.lizxing.daily.ui.English;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lizxing.daily.R;
import com.lizxing.daily.gson.English;
import com.lizxing.daily.gson.EnglishList;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private TextView textView;
    private TextView refreshText;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        initView();
        initData();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.picture);
        textView = findViewById(R.id.English_text);
        refreshText = findViewById(R.id.refresh_text);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    private void initData(){
        //标题栏
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnglishActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //背景图片
        //判断是否有缓存
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(EnglishActivity.this).load(bingPic).into(imageView);
        } else {
            requestBingPic();
        }

        //句子
        String text = prefs.getString("text", null);
        if (text != null) {
            textView.setText(text);
        } else {
            requestEnglish();
        }

        //换一句
        refreshText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestEnglish();
            }
        });

        //背景刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestBingPic();
            }
        });
    }

    /**
     * 发送请求获取必应每日一图
     */
    private void requestBingPic(){
        String urlBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(urlBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(EnglishActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(EnglishActivity.this).load(bingPic).into(imageView);
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 发起请求获取句子
     */
    private void requestEnglish(){
        String newsAddress = "http://api.tianapi.com/txapi/ensentence/?key=6634dbe82d9bddbf27123652cff14e0b";
        HttpUtil.sendOkHttpRequest(newsAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final EnglishList EnglishList = Utility.handleEnglishResponse(responseText);
                final int code = EnglishList.code;
                final String msg = EnglishList.msg;
                if (code == 200){
                    English English = EnglishList.EnglishList.get(0);
                    String text = English.enStr+'\n'+English.zhStr;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(EnglishActivity.this).edit();
                    editor.putString("text", text);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(text);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "返回数据错误"+code,Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
