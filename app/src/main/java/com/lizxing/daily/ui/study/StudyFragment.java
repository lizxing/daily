package com.lizxing.daily.ui.study;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;

public class StudyFragment extends DailyFragment {

    private LinearLayout layoutEnglish;
    private LinearLayout layoutHistory;
    private LinearLayout layoutQuotes;
    private LinearLayout layoutPoems;

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
        layoutHistory = view.findViewById(R.id.layout_history);
        layoutQuotes = view.findViewById(R.id.layout_quotes);
        layoutPoems = view.findViewById(R.id.layout_poems);
        layoutEnglish.setOnClickListener(OnEnglish);
        layoutHistory.setOnClickListener(OnHistory);
        layoutQuotes.setOnClickListener(OnQuotes);
        layoutPoems.setOnClickListener(OnPoems);
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

    /**
     * 点击历史栏
     */
    private View.OnClickListener OnHistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragment(HistoryFragment.newInstance());
        }
    };

    /**
     * 点击名言栏
     */
    private View.OnClickListener OnQuotes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragment(QuotesFragment.newInstance());
        }
    };

    /**
     * 点击诗词栏
     */
    private View.OnClickListener OnPoems = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragment(PoemsFragment.newInstance());
        }
    };

    private void showFragment(DailyFragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.study_content,fragment);
        transaction.commit();
    }



}
