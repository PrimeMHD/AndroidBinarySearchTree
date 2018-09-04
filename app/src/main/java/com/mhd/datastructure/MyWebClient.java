package com.mhd.datastructure;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class MyWebClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return true;
    }
}
