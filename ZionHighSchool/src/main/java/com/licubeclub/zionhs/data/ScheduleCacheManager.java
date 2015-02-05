package com.licubeclub.zionhs.data;

import android.content.Context;
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
