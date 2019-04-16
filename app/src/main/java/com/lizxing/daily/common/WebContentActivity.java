package com.lizxing.daily.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;

import com.lizxing.daily.R;

public class WebContentActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newscontent);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            type = bundle.getString("type");
        }
        toolbar = findViewById(R.id.toolbar);
        if(type.equals("News")){
            toolbar.setTitle(getResources().getString(R.string.news_details));
        }else if(type.equals("Articles")){
            toolbar.setTitle(getResources().getString(R.string.articles));
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String uri = getIntent().getStringExtra("uri");
        String title = getIntent().getStringExtra("title");
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(title);
        webView.loadUrl(uri);

    }

    /**
     * 点击返回键做了处理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}

