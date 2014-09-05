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

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Meal extends ActionBarActivity {

    private ProgressDialog progressDialog;
    TextView lunchmon;
    TextView lunchtue;
    TextView lunchwed;
    TextView lunchthu;
    TextView lunchfri;
    TextView dinnermon;
    TextView dinnertue;
    TextView dinnerwed;
    TextView dinnerthu;
    TextView dinnerfri;
    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];
    String[] lunchkcalstring = new String[7];
    String[] dinnerkcalstring = new String[7];
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;


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
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_meal);

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected())
        {

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
                    finish();
        }

        lunchmon = (TextView)this.findViewById(R.id.lunchmon);
        lunchtue = (TextView)this.findViewById(R.id.lunchtue);
        lunchwed = (TextView)this.findViewById(R.id.lunchwed);
        lunchthu = (TextView)this.findViewById(R.id.lunchthu);
        lunchfri = (TextView)this.findViewById(R.id.lunchfri);
        dinnermon = (TextView)this.findViewById(R.id.dinnermon);
        dinnertue = (TextView)this.findViewById(R.id.dinnertue);
        dinnerwed = (TextView)this.findViewById(R.id.dinnerwed);
        dinnerthu = (TextView)this.findViewById(R.id.dinnerthu);
        dinnerfri = (TextView)this.findViewById(R.id.dinnerfri);
        //dinnertext = (TextView)this.findViewById(R.id.dinner);



        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {
                mHandler.post(new Runnable(){

                    public void run()
                    {
                        String loading = getString(R.string.loading);
                        progressDialog = ProgressDialog.show(Meal.this,"",loading,true);
                    }
                });
                lunchstring = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Date
                lunchkcalstring = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Kcal Value
                dinnerstring = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Date
                dinnerkcalstring = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Kcal Value


                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        progressDialog.dismiss();
                        lunchmon.setText(getString(R.string.monday) + ":\n" + lunchstring[1] + "\n" + lunchkcalstring[1]);
                        lunchtue.setText(getString(R.string.tuesday) + ":\n" + lunchstring[2] + "\n" + lunchkcalstring[2]);
                        lunchwed.setText(getString(R.string.wednsday) + ":\n" + lunchstring[3] + "\n" + lunchkcalstring[3]);
                        lunchthu.setText(getString(R.string.thursday) + ":\n" + lunchstring[4] + "\n" + lunchkcalstring[4]);
                        lunchfri.setText(getString(R.string.friday) + ":\n" + lunchstring[5] + "\n" + lunchkcalstring[5]);

                        dinnermon.setText(getString(R.string.monday) + ":\n" + dinnerstring[1] + "\n" + dinnerkcalstring[1]);
                        dinnertue.setText(getString(R.string.tuesday) + ":\n" + dinnerstring[2] + "\n" + dinnerkcalstring[2]);
                        dinnerwed.setText(getString(R.string.wednsday) + ":\n" + dinnerstring[3] + "\n" + dinnerkcalstring[3]);
                        dinnerthu.setText(getString(R.string.thursday) + ":\n" + dinnerstring[4] + "\n" + dinnerkcalstring[4]);
                        dinnerfri.setText(getString(R.string.friday) + ":\n" + dinnerstring[5] + "\n" + dinnerkcalstring[5]);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }




}
