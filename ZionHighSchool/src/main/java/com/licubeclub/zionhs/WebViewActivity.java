package com.licubeclub.zionhs;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/*
* TODO - 이름 PostViewActivity 로 변경,
 * TODO - 웹사이트 전체를 보여주는대신 제목/작성자/날짜/내용만 보여주도록 수정
* */

public class WebViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView WB = (WebView)findViewById(R.id.webView);
        String ViewURL = getIntent().getStringExtra("URL");

        WB.getSettings().setJavaScriptEnabled(true); // enable javascript
        final Activity activity = this;

        WB.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        WB.loadUrl(ViewURL);

    }

}
