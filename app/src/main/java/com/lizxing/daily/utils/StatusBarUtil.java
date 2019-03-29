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
    public static void setStatusFontColor(Activity activity,boolean isLight){
        if(Build.VERSION.SDK_INT >= 21) {
            if (isLight){
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                activity.getWindow().getDecorView().setSystemUiVisibility( 1024|256);
            }
        }
    }
}
