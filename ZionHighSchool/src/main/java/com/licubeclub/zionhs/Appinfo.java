package com.licubeclub.zionhs;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.app.Activity;
import android.content.SharedPreferences;

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_appinfo);

        //Stop ShakeDetectService
        Intent intent = new Intent(this, ShakeDetectService.class);
        stopService(intent);
        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CheckBox Toggle = (CheckBox)findViewById(R.id.quickexec); //checkbox (QuickLaunch Toggle)
        CheckBox NotiToggle = (CheckBox)findViewById(R.id.quickexec_noti); // QuickLaunch Notification Toggle
        Spinner spinner = (Spinner) findViewById(R.id.quickexec_select);  //spinner
        Boolean Toggle_Boolean = pref.getBoolean("toggledata", false);
        Boolean Toggle_Noti = pref.getBoolean("notitoggle", false);
        int Spinner_int = pref.getInt("quickexec_select",0);
        Toggle.setChecked(Toggle_Boolean);
        NotiToggle.setChecked(Toggle_Noti);
        spinner.setSelection(Spinner_int);

        //Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        //Set app version name text
        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version " + app_ver);

        TextView src = (TextView)findViewById(R.id.src);
        src.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://github.com/sukso96100/zionhs"));
                startActivity(src);
            }
        });

        TextView tutorial = (TextView)findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorial = new Intent(Appinfo.this, Tutorial.class);
                startActivity(tutorial);
            }
        });



    }


            public void onStop(){
        super.onStop();

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        CheckBox check1 = (CheckBox)findViewById(R.id.quickexec);
        CheckBox noti = (CheckBox)findViewById(R.id.quickexec_noti);
        Spinner spinner = (Spinner) findViewById(R.id.quickexec_select);
        // Input values
        int quickexec_selected_value = spinner.getSelectedItemPosition();
        editor.putInt("quickexec_select", quickexec_selected_value);
        editor.putBoolean("toggledata", check1.isChecked());
        editor.putBoolean("notitoggle", noti.isChecked());
        editor.commit(); // Save values

        final boolean quicklaunchon = getSharedPreferences("pref", Context.MODE_PRIVATE).getBoolean("toggledata", true);
                if(quicklaunchon){
                    Intent intent = new Intent(this, ShakeDetectService.class);
                    startService(intent);
                }
                else{}
    }

    protected void onDestroy(){
        super.onDestroy();

    }


}