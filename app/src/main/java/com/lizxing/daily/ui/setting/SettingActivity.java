package com.lizxing.daily.ui.setting;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lizxing.daily.R;
import com.lizxing.daily.ui.MainActivity;
import com.lizxing.daily.ui.about.AboutActivity;

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        intView();
    }

    private void intView(){
        //标题栏
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //加载设置内容fragment
        getFragmentManager().beginTransaction().replace(R.id.setting_content, new SettingFragment()).commit();
    }
}
