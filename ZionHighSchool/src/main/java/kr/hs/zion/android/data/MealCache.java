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
