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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Launch Tutorial Activity If user new to this app
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Boolean firstrun = pref.getBoolean("firstrun", true);
//        if (firstrun) {
//            Intent guide = new Intent(MainActivity.this, Tutorial.class);
//            startActivity(guide);
//            SharedPreferences.Editor editor = pref.edit(); // Load Editor
//            editor.putBoolean("firstrun", false); //put value
//            editor.commit(); // Save value
//        }
//        else{
//            //Do Nothing
//        }

        View notices = findViewById(R.id.notices);
        View schoolinfo = findViewById(R.id.schoolinfo);
        View appinfo = findViewById(R.id.info);
        View meal = findViewById(R.id.meal);
        View schedule = findViewById(R.id.schedule);
        View schoolintro = findViewById(R.id.schoolintro);
        View notices_parents = findViewById(R.id.notices_parents);

        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notices.class);
                startActivity(intent);
            }
        });

        meal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Meal.class);
                startActivity(intent);
            }
        });

        schoolinfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schoolinfo.class);
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

        appinfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Appinfo.class);
                startActivity(intent);
            }
        });

        schoolintro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schoolintro.class);
                startActivity(intent);
            }
        });
        notices_parents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notices_Parents.class);
                startActivity(intent);
            }
        });

    }

}
