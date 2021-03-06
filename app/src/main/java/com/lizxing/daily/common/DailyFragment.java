package com.lizxing.daily.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public class DailyFragment extends Fragment {
    private Activity activity;

    public Context getContext() {
        if (activity == null) {
            return DailyApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
}
