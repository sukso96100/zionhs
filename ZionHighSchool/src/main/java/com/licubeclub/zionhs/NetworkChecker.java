package com.licubeclub.zionhs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by youngbin on 15. 2. 5.
 */
public class NetworkChecker {
    Context Ctx;
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;
    public NetworkChecker(Context context){
        this.Ctx = context;

    }

    public boolean isNetworkConnected(){
        cManager=(ConnectivityManager)Ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(mobile.isConnected() || wifi.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
