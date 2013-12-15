package com.licubeclub.zionhs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private ProgressDialog progressDialog;
    private ArrayList<String> titlearray;
    private ArrayList<String> titleherfarray;
    private ArrayAdapter<String> adapter;

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
        final ListView listview = (ListView) findViewById(R.id.listView);

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){}
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
            finish();
        }

        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {

                mHandler.post(new Runnable(){

                    public void run()
                    {
                        String loading = getString(R.string.loading);
                        progressDialog = ProgressDialog.show(Notices_Parents.this,"",loading,true);
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
                        progressDialog.dismiss();
                        //UI Task
                        adapter = new ArrayAdapter<String>(Notices_Parents.this,
                                android.R.layout.simple_list_item_1, titlearray);
                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(GoToWebPage);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();

    }
    private AdapterView.OnItemClickListener GoToWebPage = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long id)
        {
            String herfitem = titleherfarray.get(pos);
            Intent gotowebpage = new Intent(Intent.ACTION_VIEW);
            gotowebpage.setData(Uri.parse(herfitem));
            startActivity(gotowebpage);
        }
    };



}