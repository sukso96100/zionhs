package com.licubeclub.zionhs.data;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class MealCache extends SugarRecord<MealCache> {
    String lunch;
    String dinner;

    public MealCache(){
    }

    public MealCache(String Lunch, String Dinner){
        this.lunch = Lunch;
        this.dinner = Dinner;
    }
}
