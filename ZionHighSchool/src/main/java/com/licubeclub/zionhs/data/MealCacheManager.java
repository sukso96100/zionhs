package com.licubeclub.zionhs.data;

import android.content.Context;
import android.util.Log;

import com.licubeclub.zionhs.R;

import java.util.List;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class MealCacheManager {
    Context Ctx;
    String TAG = "MealCacheManager";
    public MealCacheManager(Context context){
        this.Ctx = context;
    }

    public void updateCache(String[] Lunch, String[] LunchKcal, String[] Dinner, String[] DinnerKcal){

        try{
        MealCache.deleteAll(MealCache.class);
        }catch (Exception e){}

        try{
            for(int i=0; i<Lunch.length; i++){
                if(Lunch[i]==null){
                    Lunch[i]=Ctx.getResources().getString(R.string.nodata);
                    LunchKcal[i] = "";
                }
                if(Dinner[i]==null){
                    Dinner[i]=Ctx.getResources().getString(R.string.nodata);
                    DinnerKcal[i] = "";
                }

                MealCache mealCacheItem = new MealCache(Lunch[i], LunchKcal[i], Dinner[i], DinnerKcal[i]);
                mealCacheItem.save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "Done Caching");
    }

    public String[] loadLunchCache(){

        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] LunchCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            LunchCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
        }
        return LunchCache;
    }

    public String[] loadLunchKcalcache(){
        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] LunchKcalCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            LunchKcalCache[i] = MealCache.findById(MealCache.class, (long) i).lunchkcal;
        }
        return LunchKcalCache;
    }

    public String[] loadDinnerCache(){
        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] DinnerCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            DinnerCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
        }
        return DinnerCache;
    }

    public String[] loadDinnerKcalCache(){
        List<MealCache> mealCache = MealCache.listAll(MealCache.class);
        String[] DinnerKcalCache = new String[mealCache.size()];
        for(int i=0; i<mealCache.size(); i++){
            DinnerKcalCache[i] = MealCache.findById(MealCache.class, (long) i).dinnerkcal;
        }
        return DinnerKcalCache;
    }
}
