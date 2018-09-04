package com.mhd.datastructure;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Activity_LearnBST extends AppCompatActivity {
    private WebView webView;
    private static final String TAG="Activity_LearnBST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_bst);
        Button button_finish_learn_bst;



        button_finish_learn_bst=(Button)findViewById(R.id.button_finish_learn_bst);
        button_finish_learn_bst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView=(WebView)findViewById(R.id.webView_learnBST);
        //webView.setBackgroundColor(2); // 设置背景色
        //webView.getBackground().setAlpha(2); // 设置填充透明度 范围：0-255

String learningURL="https://baike.baidu.com/item/%E4%BA%8C%E5%8F%89%E6%8E%92%E5%BA%8F%E6%A0%91/10905079?fr=aladdin";
webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebClient());
        webView.loadUrl(learningURL);




    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "land do nothing is ok");
            // land do nothing is ok
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "port do nothing is ok");
            // port do nothing is ok
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();//返回上个页面
            return true;
        }
        else{
            Toast.makeText(Activity_LearnBST.this,"请按下方返回键退出学习",Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
