package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.R;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.SetPhoneNumberActivity;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.ShowHistoryActivity;

/**
 * Created by xinyunxing on 12/9/2014.
 */
public class FragmentCalendar extends Fragment {

    public static final String SP_DATA = "SP_DATA";
    CalendarView calendar;
    public final String DATE_CODE = "date_code";
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar,null);

        calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        date = calendar.getDate()+"";
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            // the month is counted from 0 so it should be added 1
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).show();

                String date1 = createDate(year, month, dayOfMonth);
                if(!date.equals(date1)) {
                    date = date1;
                    Intent i = new Intent();
                    i.setClass(getActivity(), ShowHistoryActivity.class);
                    i.putExtra(DATE_CODE, date);
                    startActivity(i);
                }
            }
        });


        return rootView;
    }

    private String createDate(int year, int month, int dayOfMonth)
    {
        int mon = month+1;
        String newDay = ""+dayOfMonth;
        String newMon = ""+mon;
        if(mon<10) {
           newMon = "0"+mon;
        }
        if(dayOfMonth<10)
        {
            newDay = "0"+dayOfMonth+"";
        }
        String date = year+newMon+newDay;
        return date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendar_action, menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.action_phone_number==id)
        {
            Intent i = new Intent();
            SharedPreferences sp = this.getActivity().getSharedPreferences(SP_DATA, Context.MODE_PRIVATE);

            i.putExtra("phone",sp.getString("phone_number",""));
            i.setClass(this.getActivity(), SetPhoneNumberActivity.class);
            startActivity(i);
            //startActivityForResult(i, );
        }




        return super.onOptionsItemSelected(item);
    }
}
