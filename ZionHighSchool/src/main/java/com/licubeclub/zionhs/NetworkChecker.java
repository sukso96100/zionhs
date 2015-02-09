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
