package com.licubeclub.zionhs;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.licubeclub.zionhs.data.MealCacheManager;


public class MealActivity3 extends ActionBarActivity implements ActionBar.TabListener {
    static String[] LunchArray;
    static String[] LunchKcalArray;
    static String[] DinnerArray;
    static String[] DinnerKcalArray;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    getString(R.string.notices_info), Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.TOP, 0, 0);
//            toast.show();
        }
    };

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_activity3);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();

        /**TODO - Replace Old ActionBar.NAVIGATION_MODE_TABS with SlidingTabLayout**/
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mViewPager.setCurrentItem(0);
        Calendar Cal = Calendar.getInstance();

        int TODAY = Cal.get(Calendar.DAY_OF_WEEK);
        Log.d("TODAY", "TODAY is -" + TODAY);
        switch(TODAY){
            case 0:
                actionBar.setSelectedNavigationItem(0);
                break;
            case 1:
                actionBar.setSelectedNavigationItem(0);
                break;
            case 3:
                actionBar.setSelectedNavigationItem(1);
                break;
            case 4:
                actionBar.setSelectedNavigationItem(2);
                break;
            case 5:
                actionBar.setSelectedNavigationItem(3);
                break;
            case 6 | 7:
                actionBar.setSelectedNavigationItem(4);
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.meal_activity3, menu);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment frag = Monday.newInstance(position+1);
            switch(position){
                case 0:
                    frag = DailyMealFragment.newInstance(0,1);
                    break;
                case 1:
                    frag = DailyMealFragment.newInstance(1,2);
                    break;
                case 2:
                    frag = DailyMealFragment.newInstance(2,3);
                    break;
                case 3:
                    frag = DailyMealFragment.newInstance(3,4);
                    break;
                case 4:
                    frag = DailyMealFragment.newInstance(4,5);
                    break;

            }
            return frag;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

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
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class Monday extends Fragment{
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        private static final String ARG_SECTION_NUMBER = "section_number";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;
        String[] lunchstring = new String[7];
        String[] dinnerstring = new String[7];
        String[] lunchkcalstring = new String[7];
        String[] dinnerkcalstring = new String[7];

        TextView LunchText;
        TextView DinnerText;

        SwipeRefreshLayout SRL;

        static int DAY;

        // TODO: Rename and change types and number of parameters
        public static Monday newInstance(int sectionNumber, int day) {
            Monday fragment = new Monday();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt("day",day);
            fragment.setArguments(args);
            return fragment;
        }
        public Monday() {
            // Required empty public constructor
        }




        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            DAY = getArguments().getInt("day");
            // Inflate the layout for this fragment
            SRL = (SwipeRefreshLayout)
                    inflater.inflate(R.layout.fragment_day_meal, container, false);
            LunchText = (TextView)SRL.findViewById(R.id.lunchtxt);
            DinnerText = (TextView)SRL.findViewById(R.id.dinnertxt);
            LunchText.setText(LunchArray[1]+"\n\n"+LunchKcalArray[1]);
            DinnerText.setText(DinnerArray[1]+"\n\n"+DinnerKcalArray[1]);
            return SRL;
        }




        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }

        public static Fragment newInstance(int i) {
            return null;
        }

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            public void onFragmentInteraction(Uri uri);
        }


    }

    private void loadMealTask(){
//        SRL.setRefreshing(true);
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
//                        progressDialog.dismiss();
////                        SRL.setRefreshing(false);
//                        Log.d("Setting Text", "Setting Meal Text");
//                        Log.d("Content", LunchArray[DAY]+DinnerArray[DAY]);
//                        Log.d("Content", dinnerstring[DAY]+dinnerkcalstring[DAY]);
//                        if(lunchstring[DAY]==null){
//                            LunchText.setText(getResources().getString(R.string.nodata));
//                        }else{
//                            LunchText.setText(lunchstring[DAY] + "\n" + lunchkcalstring[DAY]);}
//                        if(dinnerstring[DAY] == null){
//                            DinnerText.setText(getResources().getString(R.string.nodata));
//                        }else{
//                            DinnerText.setText(dinnerstring[DAY] + "\n" + dinnerkcalstring[DAY]);}
//                        Log.d("DONE", "Done Setting Content");
//                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }
}