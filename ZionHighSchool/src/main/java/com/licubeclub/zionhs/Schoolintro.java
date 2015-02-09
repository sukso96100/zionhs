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
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.licubeclub.zionhs.view.SlidingTabLayout;

public class Schoolintro extends ActionBarActivity {

    private ViewPager mPager;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolintro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mPager);
    }

    @SuppressWarnings("unused")
    private void setCurrentInflateItem(int type){
        if(type==0){
            mPager.setCurrentItem(0);
        }else if(type==1){
            mPager.setCurrentItem(1);
        }
        else{
            mPager.setCurrentItem(2);
        }
    }
    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter{

        private LayoutInflater mInflater;
        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position == 0){
                v = mInflater.inflate(R.layout.schoolintro_0, null);
            }
            else if(position == 1){
                v = mInflater.inflate(R.layout.schoolintro_1, null);
            }
            else{
                v = mInflater.inflate(R.layout.schoolintro_2, null);
            }
            ((ViewPager)pager).addView(v, 0);
            return v;
        }
        @Override
        public void destroyItem(View pager, int position, Object view){
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj){
            return pager == obj;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return getString(R.string.school_symbol);
            }
            else if(position == 1){
                return getString(R.string.goal_edu);
            }
            else{
                return getString(R.string.fe_eb);
            }
        }
    }


}
