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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class QuickLauncherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_towrad_top, R.anim.zoom_out);
        setContentView(R.layout.activity_quick_launcher);

        //Stop ShakeDetectService for a few seconds
        Intent intent = new Intent(this, ShakeDetectService.class);
        stopService(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_quick_launcher);
        //Find a textView, "targetname"
        TextView targetname = (TextView)findViewById(R.id.targetname);

        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        int target = pref.getInt("quickexec_select",0);

        if(target == 0){
            targetname.setText(getString(R.string.home));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent MainActivity = new Intent(QuickLauncherActivity.this, MainActivity.class);
            overridePendingTransition(0, 0);
            startActivity(MainActivity);

            finish();
        }
        else if(target == 1){
            targetname.setText(getString(R.string.notices));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent Notices = new Intent(QuickLauncherActivity.this, Notices.class);
            startActivity(Notices);

            finish();
        }
        else if(target == 2){
            targetname.setText(getString(R.string.meal));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent Meal = new Intent(QuickLauncherActivity.this, Meal.class);
            startActivity(Meal);

            finish();
        }
        else if(target == 3){
            targetname.setText(getString(R.string.schoolinfo));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent Schoolinfo = new Intent(QuickLauncherActivity.this, Schoolinfo.class);
            startActivity(Schoolinfo);

            finish();
        }
        else if(target == 4){
            targetname.setText(getString(R.string.schedule));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent Schedule = new Intent(QuickLauncherActivity.this, Schedule.class);
            startActivity(Schedule);

            finish();
        }
        else{
            targetname.setText(getString(R.string.schoolintro));

            try {
                Thread.sleep(2000); // 1 seconds = 1000 milliseconds
            } catch (InterruptedException ignore) {}

            Intent Schoolintro = new Intent(QuickLauncherActivity.this, Schoolintro.class);
            startActivity(Schoolintro);

            finish();
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        Intent shakedetectservice = new Intent(this, ShakeDetectService.class);
        startService(shakedetectservice);
    }
}