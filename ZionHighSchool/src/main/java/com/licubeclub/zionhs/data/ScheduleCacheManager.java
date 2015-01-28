package com.licubeclub.zionhs.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class ScheduleCacheManager {

    public ScheduleCacheManager(){
    }

    public void updateCache(ArrayList<String> Date, ArrayList<String> Content){
        ScheduleCache.deleteAll(ScheduleCache.class);
        for(int i=0; i<Date.size(); i++){
            ScheduleCache scheduleCacheItem = new ScheduleCache(Date.get(i), Content.get(i));
            scheduleCacheItem.save();
        }
    }

    public ArrayList<String> loadDateCache(){
        List<ScheduleCache> scheduleCache = ScheduleCache.listAll(ScheduleCache.class);
        ArrayList<String> DateCache = new ArrayList<String>();
        for(int i=0; i<scheduleCache.size(); i++){
            DateCache.add(ScheduleCache.findById(ScheduleCache.class, (long) i).date);
        }
        return DateCache;
    }

    public ArrayList<String> loadContentCache(){
        List<ScheduleCache> scheduleCache = ScheduleCache.listAll(ScheduleCache.class);
        ArrayList<String> ContentCache = new ArrayList<String>();
        for(int i=0; i<scheduleCache.size(); i++){
            ContentCache.add(ScheduleCache.findById(ScheduleCache.class, (long) i).content);
        }
        return ContentCache;
    }
}
