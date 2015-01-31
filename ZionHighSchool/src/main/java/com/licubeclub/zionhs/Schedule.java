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
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.licubeclub.zionhs.data.ScheduleCacheManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Schedule extends ActionBarActivity {
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;
    private ArrayList<String> dayarray;
    private ArrayList<String> schedulearray;
    private ListCalendarAdapter adapter;
    private SwipeRefreshLayout SRL;
    private String URL = "http://www.zion.hs.kr/main.php?menugrp=020500&master=" +
            "diary&act=list&master_sid=1";
    private String NewURL;
    private String NextMonthURL;
    ListView listview;
    private int month;
    private int year;
    int movement = 0;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final TextView MonthTxt = (TextView)findViewById(R.id.month);
        Button Minus = (Button)findViewById(R.id.minus);
        Button Plus = (Button)findViewById(R.id.plus);

        Calendar Cal = Calendar.getInstance();
        month = Cal.get(Calendar.MONTH)+1;
        year = Cal.get(Calendar.YEAR);

        final int MONTH = month;
        final int YEAR = year;

        MonthTxt.setText(String.valueOf(year)+"."+String.valueOf(month));
        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movement--;
                NewURL = "http://www.zion.hs.kr/main.php?menugrp=020500&master=diary&act=list&master_sid=1&" +
                        "SearchYear="+YEAR+"&SearchMonth="+MONTH+"&SearchCategory=&SearchMoveMonth="+movement;
                URL = NewURL;
                networkTask();
                if(month==1){
                    month=12;
                    year--;
                }else{
                    month--;
                }
                MonthTxt.setText(String.valueOf(year)+"."+String.valueOf(month));
            }
        });
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movement++;
                NewURL = "http://www.zion.hs.kr/main.php?menugrp=020500&master=diary&act=list&master_sid=1&" +
                        "SearchYear="+YEAR+"&SearchMonth="+MONTH+"&SearchCategory=&SearchMoveMonth="+movement;
                URL = NewURL;
                networkTask();
                if(month==12){
                    month=1;
                    year++;
                }else{
                    month++;
                }
                MonthTxt.setText(String.valueOf(year)+"."+String.valueOf(month));
            }
        });

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    int skip = 0;
                    boolean skipedboolean = false;
                    schedulearray = new ArrayList<String>();
                    dayarray = new ArrayList<String>();

//                    학사일정 데이터 학교 홈페이지에서 파싱해 가져오기
                    Document doc = Jsoup.connect(URL).get();

                    Elements rawdaydata = doc.select(".listDay"); //Get contents from the class,"listDay"
                    for (Element el : rawdaydata) {
                        String daydata = el.text();
                        if(daydata.equals("") | daydata==null){
                            if(skipedboolean){
                            }else{
                                skip++;
                            }
                        }else{
                            dayarray.add(daydata); // add value to ArrayList
                            skipedboolean = true;
                        }
                    }
                    Log.d("Schedule","Parsed Day Array" + dayarray);

                    Elements rawscheduledata = doc.select(".listData"); //Get contents from tags,"a" which are in the class,"ellipsis"
                    for (Element el : rawscheduledata) {
                        String scheduledata = el.text();
                        if(skip>0){
                            skip--;
                        }else {
                            schedulearray.add(scheduledata); // add value to ArrayList
                        }
                    }
                    Log.d("Schedule","Parsed Schedule Array" + schedulearray);

//                    SRL.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
//                    SRL.setRefreshing(false);

                }
                /*
                * TODO - 데이터 캐싱
                * */

                ScheduleCacheManager manager = new ScheduleCacheManager();
                manager.updateCache(dayarray,schedulearray);

                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        //UI Task
                        adapter = new ListCalendarAdapter(Schedule.this, dayarray, schedulearray);
                        listview.setAdapter(adapter);
                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);

                    }
                });

            }
        }.start();

    }
}
