package com.licubeclub.zionhs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licubeclub.zionhs.data.MealCacheManager;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyMealFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */

/*
* TODO - 공유버튼 추가(ShareActionProvider 이용)
* */
public class DailyMealFragment extends Fragment{
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
    public static DailyMealFragment newInstance(int sectionNumber, int day) {
        DailyMealFragment fragment = new DailyMealFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt("day",day);
        fragment.setArguments(args);
        return fragment;
    }
    public DailyMealFragment() {
        // Required empty public constructor
    }


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
        SRL.setRefreshing(true);
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMealTask();
            }
        });
        loadMealTask();
        return SRL;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    private void loadMealTask(){
        Log.d("DAY","DAYNUM:"+DAY);
        SRL.setRefreshing(true);
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
                    lunchstring = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Date
                    lunchkcalstring = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","2"); //Get Lunch Menu Kcal Value
                    dinnerstring = MealLoadHelper.getMeal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Date
                    dinnerkcalstring = MealLoadHelper.getKcal("goe.go.kr","J100000659","4","04","3"); //Get Dinner Menu Kcal Value
                }catch (Exception e){}

                mHandler.post(new Runnable()
                {
                    public void run()
                    {
//                        progressDialog.dismiss();
//                        SRL.setRefreshing(false);
                        Log.d("Setting Text", "Setting Meal Text");
                        Log.d("Content", lunchstring[DAY]+lunchkcalstring[DAY]);
                        Log.d("Content", dinnerstring[DAY]+dinnerkcalstring[DAY]);
                        if(lunchstring[DAY]==null){
                            LunchText.setText(getResources().getString(R.string.nodata));
                        }else{
                            LunchText.setText(lunchstring[DAY] + "\n" + lunchkcalstring[DAY]);}
                        if(dinnerstring[DAY] == null){
                            DinnerText.setText(getResources().getString(R.string.nodata));
                        }else{
                            DinnerText.setText(dinnerstring[DAY] + "\n" + dinnerkcalstring[DAY]);}
                        Log.d("DONE", "Done Setting Content");
                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();
    }


}