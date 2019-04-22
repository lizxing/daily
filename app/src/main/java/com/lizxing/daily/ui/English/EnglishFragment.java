package com.lizxing.daily.ui.English;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.lizxing.daily.R;
import com.lizxing.daily.gson.English;
import com.lizxing.daily.gson.EnglishList;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishFragment extends Fragment {

    private TextView textView;
    private CircularProgressButton finishBtn;
    private TickerView numTtickerView;
    private TickerView gradeTickerView;
    private TickerView expTickerView;

    public static EnglishFragment newInstance() {
        EnglishFragment fragment = new EnglishFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        initView(view);
        initData();

        return view;
    }

    private void initView(View view){
        textView = view.findViewById(R.id.English_text);
        finishBtn = view.findViewById(R.id.btnFinish);
        numTtickerView = view.findViewById(R.id.tickerView_num);
        gradeTickerView = view.findViewById(R.id.tickerView_grade);
        expTickerView = view.findViewById(R.id.tickerView_exp);
    }

    private void initData(){

        //判断是否有缓存
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

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
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
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
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    editor.putString("text", text);
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(text);
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "返回数据错误"+code,Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
