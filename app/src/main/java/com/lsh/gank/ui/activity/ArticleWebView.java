package com.lsh.gank.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lsh.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * web加载页
 */

public class ArticleWebView extends Activity {

    @BindView(R.id.wv_web)
    WebView mWvWeb;
    private String mUrl;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置主题
        setTheme(MainActivity.getThemeId());

        setContentView(R.layout.layout_article_detail_webview);
        ButterKnife.bind(this);
        initData();
//        initX5();
    }

    private void initX5() {
        getWindow().setFormat(PixelFormat.TRANSPARENT);// X5设置 防止视频上屏透明
    }

    private void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mWvWeb.loadUrl(mUrl);

        mSettings = mWvWeb.getSettings();
        mSettings.setTextSize(WebSettings.TextSize.NORMAL);
        mSettings.setSupportZoom(true);
        mSettings.setJavaScriptEnabled(true);
        // 自动适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.setLoadWithOverviewMode(true);
        mWvWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWvWeb.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWvWeb != null)
            mWvWeb.destroy();
    }
}
