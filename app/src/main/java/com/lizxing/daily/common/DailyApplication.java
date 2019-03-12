package com.lizxing.daily.common;

import android.app.Application;
import android.content.Context;

public class DailyApplication extends Application {
    private static DailyApplication mInstance;
    /**
     * 获取context
     * @return
     */
    public static Context getInstance() {
        if (mInstance == null) {
            mInstance = new DailyApplication();
        }
        return mInstance;
    }

}
