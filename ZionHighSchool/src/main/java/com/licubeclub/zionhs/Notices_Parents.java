/*
 * Zion High School Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.licubeclub.zionhs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by youngbin on 13. 11. 27.
 */
public class Notices_Parents extends ActionBarActivity {
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;
    private ArrayList<String> titlearray;
    private ArrayList<String> titleherfarray;
    private ArrayAdapter<String> adapter;
    private SwipeRefreshLayout SRL;
    ListView listview;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_notices_parents);
         listview = (ListView) findViewById(R.id.listView);

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        SRL = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        SRL.setColorSchemeColors(Color.rgb(231, 76, 60),
                Color.rgb(46, 204, 113),
                Color.rgb(41, 128, 185),
                Color.rgb(241, 196, 15));
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkTask();
            }
        });

        if(mobile.isConnected() || wifi.isConnected()){}
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
            finish();
        }
        networkTask();

    }
    private AdapterView.OnItemClickListener GoToWebPage = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long id)
        {
            String herfitem = titleherfarray.get(pos);
            Intent intent = new Intent(Notices_Parents.this, WebViewActivity.class);
            intent.putExtra("URL", herfitem);
            startActivity(intent);
        }
    };

//    Method for get list of notices for parents
    private void networkTask(){
        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {

                mHandler.post(new Runnable(){

                    public void run()
                    {
                        SRL.setRefreshing(true);
                    }
                });

                //Task

                //Notices URL
                try {
                    titlearray = new ArrayList<String>();
                    titleherfarray = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.zion.hs.kr/main.php?menugrp=110100&master=bbs&act=list&master_sid=59").get();
                    Elements rawdata = doc.select(".listbody a"); //Get contents from tags,"a" which are in the class,"listbody"
                    String titlestring = rawdata.toString();
                    Log.i("Notices","Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titlherfedata = el.attr("href");
                        String titledata = el.attr("title");
                        titleherfarray.add("http://www.zion.hs.kr/" + titlherfedata); // add value to ArrayList
                        titlearray.add(titledata); // add value to ArrayList
                    }
                    Log.i("Notices","Parsed Link Array Strings" + titleherfarray);
                    Log.i("Notices","Parsed Array Strings" + titlearray);


                } catch (IOException e) {
                    e.printStackTrace();

                }


                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        //UI Task
                        adapter = new ArrayAdapter<String>(Notices_Parents.this,
                                android.R.layout.simple_list_item_1, titlearray);
                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(GoToWebPage);
                        handler.sendEmptyMessage(0);
                        SRL.setRefreshing(false);
                    }
                });

            }
        }.start();

    }

}