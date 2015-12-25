package com.nekomimi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nekomimi.R;

public class NewsInfoWebActivity extends AppCompatActivity
{
    private WebView mWebView;
    private String mUrl;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinfoweb);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_newsinfo);
        setSupportActionBar(mToolbar);
        initWebView();

        mUrl = getIntent().getStringExtra("URL");
        loadurl(mUrl);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        mUrl = intent.getStringExtra("URL");
        loadurl(mUrl);
    }

    public void loadurl(String url)
    {
        if(mUrl!=null)
        {
            mWebView.loadUrl(mUrl);
        }
    }

    public void initWebView()
    {
        mWebView = (WebView)findViewById(R.id.wv_news);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //在当前的webview中跳转到新的url
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
