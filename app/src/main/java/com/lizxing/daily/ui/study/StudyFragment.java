package com.lizxing.daily.ui.study;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.ui.English.EnglishFragment;

public class StudyFragment extends DailyFragment {

    private LinearLayout layoutEnglish;

    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
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

        return view;
    }

    private void initView(View view){
        layoutEnglish = view.findViewById(R.id.layout_english);
        layoutEnglish.setOnClickListener(OnEnglish);
    }

    /**
     * 点击英语栏
     */
    private View.OnClickListener OnEnglish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragment(EnglishFragment.newInstance());
        }
    };

    private void showFragment(DailyFragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.study_content,fragment);
        transaction.commit();
    }



}
