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