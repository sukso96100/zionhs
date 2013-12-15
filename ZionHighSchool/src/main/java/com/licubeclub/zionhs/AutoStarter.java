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

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by youngbin on 13. 12. 13.
 */
public class AutoStarter extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.i("AutoStarter","AutoStarter is Running");
        //Load notification toggle value from SharedPreferences
        boolean ToggleOn = context.getSharedPreferences("pref", Context.MODE_PRIVATE).getBoolean("toggledata", true);

        if(ToggleOn){
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Log.i("BOOTSVC", "Intent received");
                Log.i("AutoStarter","Starting ShakeDetectService");
                ComponentName cn = new ComponentName(context.getPackageName(), ShakeDetectService.class.getName());
                ComponentName svcName = context.startService(new Intent().setComponent(cn));
                if (svcName == null)
                    Log.i("AutoStarter","Cannot Start ShakeDetectService");
                    Log.e("BOOTSVC", "Could not start service " + cn.toString());
            }
        }
        else{
            //Do Nothing
        }
    }
}