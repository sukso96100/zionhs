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
package kr.hs.zion.android.data;

import android.content.Context;
import android.util.Log;

import kr.hs.zion.android.R;

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
            Log.d(TAG,"Dropping Table");
        }catch (Exception e){
            e.printStackTrace();
        }

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

                MealCache mealCacheItem = new MealCache(Lunch[i],LunchKcal[i],Dinner[i],DinnerKcal[i]);
                mealCacheItem.save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "Done Caching");
    }

    public String[] loadLunchCache(){
        try {
            List<MealCache> mealCache = MealCache.listAll(MealCache.class);
            String[] LunchCache = new String[mealCache.size()];
            for (int i = 0; i < mealCache.size(); i++) {

//            LunchCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
                LunchCache[i] = mealCache.get(i).lunch;
            }
            return LunchCache;
        }catch (Exception e){
            return null;
        }
    }

    public String[] loadLunchKcalcache(){
        try {
            List<MealCache> mealCache = MealCache.listAll(MealCache.class);
            String[] LunchKcalCache = new String[mealCache.size()];
            for (int i = 0; i < mealCache.size(); i++) {
//            LunchKcalCache[i] = MealCache.findById(MealCache.class, (long) i).lunchkcal;
                LunchKcalCache[i] = mealCache.get(i).lunchkcal;
            }
            return LunchKcalCache;
        }catch (Exception e){
            return null;
        }
    }

    public String[] loadDinnerCache(){
        try {
            List<MealCache> mealCache = MealCache.listAll(MealCache.class);
            String[] DinnerCache = new String[mealCache.size()];
            for (int i = 0; i < mealCache.size(); i++) {
//            DinnerCache[i] = MealCache.findById(MealCache.class, (long) i).lunch;
                DinnerCache[i] = mealCache.get(i).dinner;
            }
            return DinnerCache;
        }catch (Exception e){
            return null;
        }
    }

    public String[] loadDinnerKcalCache(){
        try {
            List<MealCache> mealCache = MealCache.listAll(MealCache.class);
            String[] DinnerKcalCache = new String[mealCache.size()];
            for (int i = 0; i < mealCache.size(); i++) {
//            DinnerKcalCache[i] = MealCache.findById(MealCache.class, (long) i).dinnerkcal;
                DinnerKcalCache[i] = mealCache.get(i).dinnerkcal;
            }
            return DinnerKcalCache;
        }catch (Exception e){
            return null;
        }
    }
}
