package com.lizxing.daily.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.lizxing.daily.R;

public class SettingFragment extends PreferenceFragment {
    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定设置xml
        addPreferencesFromResource(R.xml.setting);
    }

}

