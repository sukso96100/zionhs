package com.licubeclub.zionhs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by youngbin on 13. 11. 27.
 */
public class Notices extends Activity {
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;
    private ProgressDialog progressDialog;
    String CommonAttr = new String("align=\"left\" style=\"padding-left:0ox;\" ");
    String EncodingType;
    private ArrayList<HashMap<String, String>> data;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        data = new ArrayList<HashMap<String, String>>();


        if(mobile.isConnected() || wifi.isConnected())
        {

        }
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
                        progressDialog = ProgressDialog.show(Notices.this, "", loading, true);
                    }
                });
                //Task

                //Notices URL
                String SRCURL = "http://www.zion.hs.kr/main.php?menugrp=110100&master=bbs&act=list&master_sid=58";
                try {
                    Source HTML_Source = new Source(new URL(SRCURL));
                    //Set Encoding Type
                    if(HTML_Source.getEncoding().equals("Cp1252")){
                        EncodingType = new String("EUC-KR");
                    }
                    else{
                        EncodingType = new String(HTML_Source.getEncoding());
                    }
                    //Extract Table tags and Td tags
                    Element TableTags1 = HTML_Source.getAllElements(HTMLElementName.TABLE).get(0);
                    List<Element> TdTags1 = HTML_Source.getAllElements(HTMLElementName.TD);

                    for(int i=0; i<TdTags1.size(); i++){
                        Element TdElement = TdTags1.get(1);
                        /*
                        if (!TdElement.getAttributes().toString().contains(CommonAttr)) {
                            continue;
                        }
                        */
                        String notices_text = new String(
                                TdElement.getTextExtractor().toString().getBytes(HTML_Source.getEncoding()),EncodingType
                        );
                        Log.d("title", notices_text);
                        System.out.println(notices_text);

                        //Get links of titles
                        List<Element> Atags = TdElement.getAllElements(HTMLElementName.A);
                        String herf = Atags.get(0).getAttributeValue("herf");
                        Log.d("links", herf);
                        System.out.println(herf);


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        progressDialog.dismiss();
                        //UI Task

                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }
}