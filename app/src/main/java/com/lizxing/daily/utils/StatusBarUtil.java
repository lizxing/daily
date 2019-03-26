package com.lizxing.daily.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.lizxing.daily.R;

public class StatusBarUtil {

    public static void setStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
