package com.licubeclub.zionhs;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;

import com.licubeclub.zionhs.meal.MondayMeal;


public class MealActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO 탭호스트 셋업 오류 수정
        setContentView(R.layout.activity_meal2);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        //탭 추가
        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
                MondayMeal.class, null);
    }
}