package com.licubeclub.zionhs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class Schedule extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_schedule);

    }






}
