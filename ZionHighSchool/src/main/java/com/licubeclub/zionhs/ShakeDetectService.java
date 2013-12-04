package com.licubeclub.zionhs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by youngbin on 13. 12. 1.
 */
public class ShakeDetectService extends Service {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    public void onCreate() {
        Log.d("ShakeDetectService","Service Started");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.d("ShakeDetectService","Shaken!");
                //Following thing will be executed when shaking detected

                //vibrate
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                //Run "QuickLauncherActivity"
                Intent intent = new Intent(ShakeDetectService.this, QuickLauncherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
        return flags;

    }



    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(mShakeDetector);
    }
}