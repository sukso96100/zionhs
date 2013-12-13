package com.licubeclub.zionhs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class Schoolinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_schoolinfo);

        View maps_card = findViewById(R.id.maps_card);
        TextView homepage = (TextView)findViewById(R.id.homepage);

        maps_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse(
                        "https://www.google.com/maps/preview#!q=%EC%8B%9C%EC%98%A8%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90&data=!4m15!2m14!1m13!1s0x357b631fc929cead%3A0x3ebc17cda0be11bd!3m8!1m3!1d32737846!2d-95.677068!3d37.0625!3m2!1i1920!2i964!4f13.1!4m2!3d37.470904!4d126.805683"
                ));
                startActivity(src);
            }
        });

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse(
                        "http://zion.hs.kr"
                ));
                startActivity(src);
            }
        });

    }





}
