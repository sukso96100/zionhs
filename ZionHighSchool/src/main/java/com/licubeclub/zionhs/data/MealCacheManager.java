package com.licubeclub.zionhs.data;

import android.content.Context;

import java.util.List;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class MealCacheManager {

    public MealCacheManager(){
    }

    public void updateCache(String[] Lunch, String[] Dinner){
        MealCache.deleteAll(MealCache.class);
        for(int i=0; i<Lunch.length; i++){
            MealCache mealCacheItem = new MealCache(Lunch[i], Dinner[i]);
            mealCacheItem.save();
        }
    }

    public String[] loadLunchCache(){

        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] LunchCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            LunchCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
        }
        return LunchCache;
    }

    public String[] loadDinnerCache(){
        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] DinnerCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            DinnerCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
        }
        return DinnerCache;
    }
}
