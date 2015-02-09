/*
 * Zion High School Application for Android
 * Copyright (C) 2013-2015 The Zion High School Application for Android Open Source Project
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
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import kr.hs.zion.android.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
/*
* TODO - 중식/석식, 학사일정 알림서비스 추가(이건 가장 마지막에 시간 날떄)
* */
public class MainActivity extends ActionBarActivity {
    private DrawerLayout NavigationDrawer;
    private ActionBarDrawerToggle DrawerToggle;
    private ListView DrawerList;
    private ArrayList<String> DrawerArray;
    private ArrayList<Drawable> IconArray;
    private DrawerListAdapter Adapter;
    private Boolean isNavDrawerOpen = false;

    String MealString;
    String ScheduleString;
    String NoticesParentString;
    String NoticeString;

    int AMorPM;
    int DAYofWEEK;
    int DAYofMONTH;

    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];

    private String URL = "http://www.zion.hs.kr/main.php?menugrp=020500&master=" +
            "diary&act=list&master_sid=1";
    private ArrayList<String> dayarray;
    private ArrayList<String> schedulearray;

    private ArrayList<String> titlearray_np;
    private ArrayList<String> titlearray_n;

    private TextView MEAL;
    private TextView SCHEDULE;
    private TextView NOTIPARNTS;
    private TextView NOTICES;

    private SwipeRefreshLayout SRL;

    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar Cal = Calendar.getInstance();
        AMorPM = Cal.get(Calendar.AM_PM);
        DAYofWEEK = Cal.get(Calendar.DAY_OF_WEEK);
        DAYofMONTH = Cal.get(Calendar.DAY_OF_MONTH);

        Log.d("DAYofMONTH",String.valueOf(Cal.get(Calendar.DAY_OF_MONTH)));

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        setContentView(R.layout.activity_main);

        /*
        * TODO - 시간에 따른 툴바 배경 설정(아침/점심/저녁)
        * */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MEAL = (TextView)findViewById(R.id.mealdata);
        SCHEDULE = (TextView)findViewById(R.id.schedata);
        NOTIPARNTS = (TextView)findViewById(R.id.notiparentdata);
        NOTICES  = (TextView)findViewById(R.id.notidata);

        View notices = findViewById(R.id.notices);
        View meal = findViewById(R.id.meal);
        View schedule = findViewById(R.id.schedule);
        View notices_parents = findViewById(R.id.notices_parents);

        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(MainActivity.this, Notices.class);
                intent0.putExtra("url","http://www.zion.hs.kr/main.php?menugrp=110100&master=bbs&act=list&master_sid=58");
                intent0.putExtra("title",getResources().getString(R.string.notices));
                startActivity(intent0);
            }
        });

        meal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MealActivity.class);
                startActivity(intent);
            }
        });



       schedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
            }
        });




        notices_parents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notices.class);
                intent.putExtra("url","http://www.zion.hs.kr/main.php?menugrp=110200&master=bbs&act=list&master_sid=59");
                intent.putExtra("title", getResources().getString(R.string.title_activity_notices__parents));
                startActivity(intent);
            }
        });



        //Navigation Drawer
        DrawerArray = new ArrayList<String>();
        DrawerArray.add(getString(R.string.meal));
        DrawerArray.add(getString(R.string.schedule));
        DrawerArray.add(getString(R.string.title_activity_notices__parents));
        DrawerArray.add(getString(R.string.notices));
        DrawerArray.add(getString(R.string.schoolintro));
        DrawerArray.add(getString(R.string.schoolinfo));
        DrawerArray.add(getString(R.string.appsettings_apinfo_title));

        IconArray = new ArrayList<Drawable>();
        IconArray.add(getResources().getDrawable(R.drawable.ic_meal));
        IconArray.add(getResources().getDrawable(R.drawable.ic_event_black_24dp));
        IconArray.add(getResources().getDrawable(R.drawable.ic_insert_drive_file_black_24dp));
        IconArray.add(getResources().getDrawable(R.drawable.ic_speaker_notes_black_24dp));
        IconArray.add(getResources().getDrawable(R.drawable.ic_intro));
        IconArray.add(getResources().getDrawable(R.drawable.ic_school));
        IconArray.add(getResources().getDrawable(R.drawable.ic_info_black_24dp));


        NavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerList = (ListView) findViewById(R.id.left_drawer);

        Adapter = new DrawerListAdapter(this, DrawerArray, IconArray);
        DrawerList.setAdapter(Adapter);

        //Listen for Navigation Drawer State
        DrawerToggle = new ActionBarDrawerToggle(this,
                NavigationDrawer, R.string.drawer_open, R.string.drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                isNavDrawerOpen = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setBackgroundDrawable(Darkblue);
                isNavDrawerOpen = true;
            }

        };
        NavigationDrawer.setDrawerListener(DrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Drawer Item Click action
        DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, MealActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, Schedule.class));
                        break;
                    case 2:
                        Intent intent = new Intent(MainActivity.this, Notices.class);
                        intent.putExtra("url","http://www.zion.hs.kr/main.php?menugrp=110200&master=bbs&act=list&master_sid=59");
                        intent.putExtra("title", getResources().getString(R.string.title_activity_notices__parents));
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent0 = new Intent(MainActivity.this, Notices.class);
                        intent0.putExtra("url","http://www.zion.hs.kr/main.php?menugrp=110100&master=bbs&act=list&master_sid=58");
                        intent0.putExtra("title",getResources().getString(R.string.notices));
                        startActivity(intent0);
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, Schoolintro.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, Schoolinfo.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, Appinfo.class));
                        break;
                }
            }
        });

        SRL = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        if(mobile.isConnected() || wifi.isConnected()){
            networkTask();
        }
        else{

            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(mobile.isConnected() || wifi.isConnected()){
                    networkTask();
                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    SRL.setRefreshing(false);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(DrawerToggle.onOptionsItemSelected(item)){
            if(!isNavDrawerOpen){
                NavigationDrawer.openDrawer(Gravity.LEFT);
            }
            else{
                NavigationDrawer.closeDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        DrawerToggle.syncState();
    }

    @Override
    public void onBackPressed(){
        if(isNavDrawerOpen){
            NavigationDrawer.closeDrawer(Gravity.LEFT);
        }else{
            finish();
        }
    }

    void networkTask(){
        SRL.setRefreshing(true);
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
            }
        };

        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {
                mHandler.post(new Runnable(){

                    public void run()
                    {
                        // SRL.setRefreshing(true);
                    }
                });
                try{
                    lunchstring = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Date
                    dinnerstring = MealLoadHelper.getMeal("goe.go.kr", "J100000659", "4", "04", "3"); //Get Dinner Menu Date
                }catch (Exception e){}

                try {
                    int skipcount = 0;
                    boolean skipped = false;
                    schedulearray = new ArrayList<String>();
                    dayarray = new ArrayList<String>();

//                    학사일정 데이터 학교 홈페이지에서 파싱해 가져오기
                    Document doc = Jsoup.connect(URL).get();

                    Elements rawdaydata = doc.select(".listDay"); //Get contents from the class,"listDay"
                    for (Element el : rawdaydata) {
                        String daydata = el.text();
                        if(daydata.equals("") | daydata==null){
                            if(skipped){
                            }else {
                                skipcount++;
                            }
                        }else{
                            dayarray.add(daydata); // add value to ArrayList
                            skipped = true;
                        }
                    }
                    Log.d("Schedule","Parsed Day Array" + dayarray);

                    Elements rawscheduledata = doc.select(".listData"); //Get contents from tags,"a" which are in the class,"ellipsis"
                    for (Element el : rawscheduledata) {
                        String scheduledata = el.text();
                        if(skipcount>0){
                            skipcount--;
                        }else{
                            schedulearray.add(scheduledata); // add value to ArrayList
                        }
                    }
                    Log.d("Schedule","Parsed Schedule Array" + schedulearray);
//                    SRL.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
//                    SRL.setRefreshing(false);

                }
                try {
                    titlearray_np = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.zion.hs.kr/main.php?menugrp=110100&master=bbs&act=list&master_sid=59").get();
                    Elements rawdata = doc.select(".listbody a"); //Get contents from tags,"a" which are in the class,"listbody"
                    String titlestring = rawdata.toString();
                    Log.i("Notices","Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titledata = el.attr("title");
                        titlearray_np.add(titledata); // add value to ArrayList
                    }
                    Log.i("Notices","Parsed Array Strings" + titlearray_np);


                } catch (IOException e) {
                    e.printStackTrace();

                }
                //Notices URL
                try {
                    titlearray_n = new ArrayList<String>();
                    //파싱할 페이지 URL
                    Document doc = Jsoup.connect("http://www.zion.hs.kr/main.php?" +
                            "menugrp=110100&master=bbs&act=list&master_sid=58").get();
                    //Get contents from tags,"a" which are in the class,"listbody"
                    Elements rawmaindata = doc.select(".listbody a");
                    String titlestring = rawmaindata.toString();
                    Log.i("Notices","Parsed Strings" + titlestring);


                    //파싱할 데이터로 배열 생성
                    for (Element el : rawmaindata) {
                        String titledata = el.attr("title");
                        titlearray_n.add(titledata); // add value to ArrayList
                    }
                    Log.i("Notices","Parsed Array Strings" + titlearray_n);


                } catch (IOException e) {
                    e.printStackTrace();

                }

                mHandler.post(new Runnable()
                {
                    public void run()
                    {
//                        progressDialog.dismiss();
//                        SRL.setRefreshing(false);
                        if(AMorPM==Calendar.AM){
                            MealString = lunchstring[DAYofWEEK - 1];
                        }else{
                            MealString = dinnerstring[DAYofWEEK - 1];
                        }
                        try {
                            ScheduleString = schedulearray.get(DAYofMONTH - 1);
                            NoticesParentString = titlearray_np.get(0);
                            NoticeString = titlearray_n.get(0);
                        }catch (Exception e){
                            ScheduleString = getResources().getString(R.string.error);
                            NoticesParentString = getResources().getString(R.string.error);
                            NoticeString = getResources().getString(R.string.error);
                        }

                            if (MealString == null) {
                                MealString = getResources().getString(R.string.nodata);
                            }else if(MealString.equals("")){
                                MealString = getResources().getString(R.string.nodata);
                            }
                            if (ScheduleString == null) {
                                ScheduleString = getResources().getString(R.string.nodata);
                            }else if(ScheduleString.equals("")){
                                ScheduleString = getResources().getString(R.string.nodata);
                            }
                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);
                        setContentData();
                    }
                });

            }
        }.start();

    }

    void setContentData(){
        MEAL.setText(MealString);
        SCHEDULE.setText(ScheduleString);
        NOTIPARNTS.setText(NoticesParentString);
        NOTICES.setText(NoticeString);
    }

}
