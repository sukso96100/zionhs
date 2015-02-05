package com.licubeclub.zionhs;

import java.util.Locale;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licubeclub.zionhs.data.MealCacheManager;
import com.licubeclub.zionhs.view.SlidingTabLayout;

import org.w3c.dom.Text;


public class MealActivity extends ActionBarActivity {

    String LOG_TAG = "MealActivity";
    NetworkChecker NetCheck;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    SwipeRefreshLayout SRL;

    static String[] LunchArray;
    static String[] LunchKcalArray;
    static String[] DinnerArray;
    static String[] DinnerKcalArray;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        SRL = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        NetCheck = new NetworkChecker(MealActivity.this);

        loadMealTask();

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMealTask();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 5;
        }

        /**
         * @return true if the value returned from
         *         {@link #instantiateItem(ViewGroup, int)} is the same object
         *         as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important
         * as what this method returns is what is displayed in the
         * {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real
         * application the title should refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.monday).toUpperCase(l);
                case 1:
                    return getString(R.string.tuesday).toUpperCase(l);
                case 2:
                    return getString(R.string.wednsday).toUpperCase(l);
                case 3:
                    return getString(R.string.thursday).toUpperCase(l);
                case 4:
                    return getString(R.string.friday).toUpperCase(l);
            }
            return null;
        }

        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at
         * {@code position}. Here we inflate a layout from the apps resources
         * and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources

            View view = getLayoutInflater().inflate(R.layout.fragment_day_meal,
                    container, false);
            TextView LunchTxt = (TextView)view.findViewById(R.id.lunchtxt);
            TextView DinnerTxt = (TextView)view.findViewById(R.id.dinnertxt);

            if(LunchArray==null){
                LunchTxt.setText(getResources().getString(R.string.not_loaded_yet));
            }else if(LunchArray[position+1]==null){
                LunchTxt.setText(getResources().getString(R.string.nodata));
            }else{
                String LunchData = LunchArray[position+1]+"\n\n"+LunchKcalArray[position+1];
                LunchTxt.setText(LunchData);
            }

            if(DinnerArray==null){
             DinnerTxt.setText(getResources().getString(R.string.not_loaded_yet));
            }else if(DinnerArray[position+1]==null){
                DinnerTxt.setText(getResources().getString(R.string.nodata));
            }else{
                String DinnerData = DinnerArray[position+1]+"\n\n"+DinnerKcalArray[position+1];
                DinnerTxt.setText(DinnerData);
            }
            container.addView(view);

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is
         * simply removing the {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }

    private void loadMealTask(){
        final MealCacheManager manager = new MealCacheManager(MealActivity.this);
        SRL.setRefreshing(false);
        SRL.setRefreshing(true);
        if(NetCheck.isNetworkConnected()){
        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {
                mHandler.post(new Runnable(){

                    public void run()
                    {
                        // SRL.setRefreshing(true);
                    }
                });
                try{
                    LunchArray = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Date
                    LunchKcalArray = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Kcal Value
                    DinnerArray = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Date
                    DinnerKcalArray = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Kcal Value
                }catch (Exception e){}

                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        mViewPager.setAdapter(new SamplePagerAdapter());
                        mSlidingTabLayout.setViewPager(mViewPager);
                        manager.updateCache(LunchArray, LunchKcalArray, DinnerArray, DinnerKcalArray);
                        Log.d("MealLoadTadk","Data Loading Done");
                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();

       }else{
            //Load from Cache
            LunchArray = manager.loadLunchCache();
            LunchKcalArray = manager.loadLunchKcalcache();
            DinnerArray = manager.loadDinnerCache();
            DinnerKcalArray = manager.loadDinnerKcalCache();
            mViewPager.setAdapter(new SamplePagerAdapter());
            mSlidingTabLayout.setViewPager(mViewPager);
            SRL.setRefreshing(false);
        }
    }

}