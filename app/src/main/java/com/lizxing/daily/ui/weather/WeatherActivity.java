package com.lizxing.daily.ui.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.lizxing.daily.R;
import com.lizxing.daily.gson.Forecast;
import com.lizxing.daily.gson.Weather;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    public SwipeRefreshLayout swipeRefresh;
    private TextView weatherCity;
    private TextView weatherUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView qltyText;
    private TextView tmpText;
    private TextView flText;
    private TextView winddirText;
    private TextView windscText;
    private TextView windspdText;
    private TextView humText;
    private TextView pcpnText;
    private TextView visText;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private String mWeatherId;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        状态栏与背景融合
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_weather);

        initView();
        initData();
    }

    private void initView(){
//        weatherLayout = findViewById(R.id.weather_layout);
        weatherCity = findViewById(R.id.weather_city);
        weatherUpdateTime = findViewById(R.id.weather_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        qltyText = findViewById(R.id.qlty_text);
        flText = findViewById(R.id.now_fl);
        tmpText = findViewById(R.id.now_tmp);
        winddirText = findViewById(R.id.now_wind_dir);
        windscText = findViewById(R.id.now_wind_sc);
        windspdText = findViewById(R.id.now_wind_spd);
        humText = findViewById(R.id.now_hum);
        pcpnText = findViewById(R.id.now_pcpn);
        visText = findViewById(R.id.now_vis);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        toolbar = findViewById(R.id.toolbar);

    }
    private void initData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
//            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        String bingPic = prefs.getString("bing_pic", null);


        toolbar.setTitle(getResources().getString(R.string.weather));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息。
     */
    public void requestWeather(final String weatherId) {
        String weatherAddress = "http://guolin.tech/api/weather?cityid=" + "CN101280101" + "&key=2ef89d38b1494f0c896b3c43bb849cea";
        HttpUtil.sendOkHttpRequest(weatherAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "响应失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        String fl = weather.now.fl;
        String windDir = weather.now.windDir;
        String windSc = weather.now.windSc;
        String windSpd = weather.now.windSpd;
        String hum = weather.now.hum;
        String pcpn = weather.now.pcpn;
        String vis = weather.now.vis;

        weatherCity.setText(cityName);
        weatherUpdateTime.setText(getResources().getString(R.string.update_time)+updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.weather_forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
            qltyText.setText(weather.aqi.city.qlty);
        }
        tmpText.setText(degree);
        flText.setText(fl);
        winddirText.setText(windDir);
        windscText.setText(windSc);
        windspdText.setText(windSpd);
        humText.setText(hum);
        pcpnText.setText(pcpn);
        visText.setText(vis);
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
    }


}
