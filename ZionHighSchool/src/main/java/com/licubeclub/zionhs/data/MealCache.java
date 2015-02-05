package com.licubeclub.zionhs.data;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class MealCache extends SugarRecord<MealCache> {
    String lunch;
    String lunchkcal;
    String dinner;
    String dinnerkcal;

    public MealCache(){
    }

    public MealCache(String Lunch, String LunchKcal, String Dinner, String DinnerKcal){
        this.lunch = Lunch;
        this.lunchkcal = LunchKcal;
        this.dinner = Dinner;
        this.dinnerkcal = DinnerKcal;
    }
}
