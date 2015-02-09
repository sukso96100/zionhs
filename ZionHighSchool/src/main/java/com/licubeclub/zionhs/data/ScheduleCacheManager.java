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
package com.licubeclub.zionhs.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class ScheduleCacheManager {

    public ScheduleCacheManager(){

    }

    public void updateCache(ArrayList<String> Date, ArrayList<String> Content){
        try {
            ScheduleCache.deleteAll(ScheduleCache.class);
        }catch(Exception e){
            Log.d("ERROR","Error Updating Cache");
            e.printStackTrace();
        }

        try{
            for (int i = 0; i < Date.size(); i++) {
                String DATE, CONTENT;
                if(Date.get(i)==null){
                    DATE="-";
                }else{
                    DATE=Date.get(i);
                }
                if(Content.get(i)==null){
                    CONTENT="-";
                }else{
                    CONTENT=Content.get(i);
                }
                ScheduleCache scheduleCacheItem = new ScheduleCache(DATE, CONTENT);
                scheduleCacheItem.save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadDateCache(){
        try {
            List<ScheduleCache> scheduleCache = ScheduleCache.listAll(ScheduleCache.class);
            ArrayList<String> DateCache = new ArrayList<String>();
            for (int i = 0; i < scheduleCache.size(); i++) {
                String Item = scheduleCache.get(i).date;
                if (Item == null) {
                    Item = "-";
                }
                DateCache.add(Item);
            }
            return DateCache;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<String> loadContentCache(){
        try {
            List<ScheduleCache> scheduleCache = ScheduleCache.listAll(ScheduleCache.class);
            ArrayList<String> ContentCache = new ArrayList<String>();
            for (int i = 0; i < scheduleCache.size(); i++) {
                String Item = scheduleCache.get(i).content;
                if (Item == null) {
                    Item = "-";
                }
                ContentCache.add(Item);
            }
            return ContentCache;
        }catch (Exception e){
            return null;
        }
    }
}
