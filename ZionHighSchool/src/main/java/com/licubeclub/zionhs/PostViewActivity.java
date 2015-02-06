package com.licubeclub.zionhs;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import  android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.licubeclub.zionhs.data.ScheduleCacheManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class PostViewActivity extends ActionBarActivity {
    String URL;
    WebView WV;
    String data;
    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        WV = (WebView)findViewById(R.id.webView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        String Title = getIntent().getStringExtra("title");
        String Date = getIntent().getStringExtra("date");
        String Author = getIntent().getStringExtra("author");
         URL = getIntent().getStringExtra("URL");
        toolbar.setTitle(Title);
        toolbar.setSubtitle(Author+" - "+Date);
        setSupportActionBar(toolbar);
        networkTask();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_view, menu);
        // 공유 버튼 찾기
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // ShareActionProvider 얻기
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // 공유 버튼에 사용할 Intent 를 만들어 주는 메서드를 호출합니다.
        if (mShareActionProvider != null ) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.open) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareIntent() {
        //액션은 ACTION_SEND 로 합니다.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //Flag 를 설정해 줍니다. 공유하기 위해 공유에 사용할 다른 앱의 하나의 Activity 만 열고,
        //다시 돌아오면 열었던 Activity 는 꺼야 하기 때문에
        //FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET 로 해줍니다.
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //공유할 것의 형태입니다. 우리는 텍스트를 공유합니다.
        shareIntent.setType("text/plain");


        //보낼 데이터를 Extra 로 넣어줍니다.
        shareIntent.putExtra(Intent.EXTRA_TEXT,URL);
        return shareIntent;
    }

    private void networkTask(){

        final Handler mHandler = new Handler();

        new Thread()
        {
            public void run()
            {

                try {
                    Document doc = Jsoup.connect(URL).get();
                    Element element = doc.select("td").get(4);
                   data = element.getAllElements().toString();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        WV.getSettings().setJavaScriptEnabled(true);
                        WV.setWebViewClient(new WebViewClient() {
                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                Toast.makeText(PostViewActivity.this, description, Toast.LENGTH_SHORT).show();
                            }
                        });
//                        WV.loadData(data,"text/html","utf-8");
                        if(data==null){
                            data = getResources().getString(R.string.nodata);
                        }else if(data.equals("<td class=\"writeBody writeContent\"></td>")){
                            data = getResources().getString(R.string.nodata);
                        }else{}
                        Log.d("DATA",data);
                        WV.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();

    }
}
