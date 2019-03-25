package com.lizxing.daily.ui.picture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lizxing.daily.R;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.ui.weather.WeatherActivity;
import com.lizxing.daily.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PictureActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        initView();
    }

    private void initView(){
        //标题栏
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PictureActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //图片
        imageView = findViewById(R.id.picture);
        loadBingPic();
    }

    /**
     * 获取必应每日一图
     */
    private void loadBingPic(){
        String urlBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(urlBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(PictureActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(PictureActivity.this).load(bingPic).into(imageView);
                    }
                });
            }
        });
    }
}
