package com.lizxing.daily.ui.articles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizxing.daily.R;


public class ArticlesPageFragment extends Fragment {

    private static final String TAG = "ArticlesPageFragment";
    public static final String PAGE = "PAGE";
    private int mPage;

    public static ArticlesPageFragment newInstance(int page) {
        ArticlesPageFragment pagefragment = new ArticlesPageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        pagefragment.setArguments(args);
        return pagefragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(PAGE); //获取传入的值
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles_page, container, false);
    }

}
