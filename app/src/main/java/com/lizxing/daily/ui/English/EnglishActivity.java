package com.lizxing.daily.ui.English;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.lizxing.daily.R;
import com.lizxing.daily.gson.English;
import com.lizxing.daily.gson.EnglishList;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.StatusBarUtil;
import com.lizxing.daily.utils.Utility;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;
    private CircularProgressButton finishBtn;
    private TickerView numTtickerView;
    private TickerView gradeTickerView;
    private TickerView expTickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        //设置状态栏颜色
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.English_toolbar));
        initView();
        initData();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.English_text);
        finishBtn = findViewById(R.id.btnFinish);
        numTtickerView = findViewById(R.id.tickerView_num);
        gradeTickerView = findViewById(R.id.tickerView_grade);
        expTickerView = findViewById(R.id.tickerView_exp);
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


        //判断是否有缓存
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //句子
        String text = prefs.getString("text", null);
        if (text != null) {
            textView.setText(text);
        } else {
            requestEnglish();
        }

        //数字
        numTtickerView.setCharacterLists(TickerUtils.provideNumberList());
        gradeTickerView.setCharacterLists(TickerUtils.provideNumberList());
        expTickerView.setCharacterLists(TickerUtils.provideNumberList());
        numTtickerView.setAnimationDuration(500);
        numTtickerView.setAnimationInterpolator(new OvershootInterpolator());
        numTtickerView.setGravity(Gravity.START);
        int number = prefs.getInt("number",0);
        numTtickerView.setText(String.valueOf(number));
        int exp = prefs.getInt("exp",0);
        expTickerView.setText(String.valueOf(exp));
        gradeTickerView.setText(String.valueOf(exp / 100));


        //按钮
        finishBtn.setText(getResources().getString(R.string.Finish));
        finishBtn.setIdleText(getResources().getString(R.string.Finish));
        finishBtn.setCompleteText(getResources().getString(R.string.Next));
        finishBtn.setIndeterminateProgressMode(true); // 进入不精准进度模式
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularProgressButton btn = (CircularProgressButton) v;
                int progress = btn.getProgress();
                if (progress == 0) {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
                    valueAnimator.setDuration(3000);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            int value = (int) valueAnimator.getAnimatedValue();
                            btn.setProgress(value);
                            if(value == 100){
                                //更新数字
                                int number = prefs.getInt("number",0);
                                int exp = prefs.getInt("exp",0);
                                ++number;
                                if (text != null) {
                                    exp += text.length();//句子长度决定经验值
                                }
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(EnglishActivity.this).edit();
                                editor.putInt("number", number);
                                editor.putInt("exp", exp);
                                editor.apply();
                                numTtickerView.setText(String.valueOf(number));
                                expTickerView.setText(String.valueOf(exp));
                                gradeTickerView.setText(String.valueOf(exp / 100));
                            }
                        }
                    });
                    valueAnimator.start();
                } else {
                    //进度完成
                    btn.setProgress(0);
                    //更新句子
                    requestEnglish();
                }
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
