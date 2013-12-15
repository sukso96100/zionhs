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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class ShakeDetectService extends Service {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private static final int MY_NOTIFICATION_ID=1;
    private NotificationManager notificationManager;
    private Notification myNotification;

    @Override
    public void onCreate() {
        //Load notification toggle value from SharedPreferences
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Boolean NotiOn = pref.getBoolean("notitoggle", true);
        Log.d("ShakeDetectService","Service Started");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.d("ShakeDetectService","Shaken!");

                //Following thing will be executed when shaking detected
                //vibrate
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                //Run "QuickLauncherActivity"
                Intent intent = new Intent(ShakeDetectService.this, QuickLauncherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }
        });

        if(NotiOn){
            //Show Notification
            notificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            myNotification = new Notification(R.drawable.ic_stat_quicklaunch,
                    getText(R.string.noti_shake_start),
                    System.currentTimeMillis());
            Context context = getApplicationContext();
            CharSequence notificationTitle = getText(R.string.noti_shake_title);
            CharSequence notificationText = getText(R.string.noti_shake_title_sub);
            Intent myIntent = new Intent(getBaseContext(), Appinfo.class);;
            PendingIntent pendingIntent
                    = PendingIntent.getActivity(getBaseContext(),
                    0, myIntent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            myNotification.defaults |= Notification.DEFAULT_SOUND;
            myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
            myNotification.flags = Notification.FLAG_ONGOING_EVENT;
            myNotification.setLatestEventInfo(context,
                    notificationTitle,
                    notificationText,
                    pendingIntent);
            notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
        }
        else{
            //Do Nothing
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
        return flags;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Load notification toggle value from SharedPreferences
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Boolean NotiOn = pref.getBoolean("notitoggle", true);

        mSensorManager.unregisterListener(mShakeDetector);
        if(NotiOn){
            notificationManager.cancel(MY_NOTIFICATION_ID);
        }
        else{
            //Do Nothing
        }
    }
}