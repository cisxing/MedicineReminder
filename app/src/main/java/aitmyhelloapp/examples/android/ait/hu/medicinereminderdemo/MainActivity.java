package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter.MyPagerAdapter;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;


public class MainActivity extends FragmentActivity {

    public static final String SP_DATA = "SP_DATA";
    ArrayList<PendingIntent> intentArray;
    ArrayList<PendingIntent> secondArray;
    AlarmManager alarmManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmService();

    }


    public void alarmService()
    {
        intentArray= new ArrayList<PendingIntent>();
        secondArray = new ArrayList<PendingIntent>();

        List<MedicineInfo> infos = MedicineInfo.listAll(MedicineInfo.class);
        for(int i =0; i<infos.size();i++) {
           boolean set = false;
            MedicineInfo currentInfo = infos.get(i);

            Intent myIntent = new Intent(MainActivity.this, TimeReceiver.class);
            //probably not going to work
            myIntent.putExtra("medicine_name",currentInfo.getMedicineName());

            PendingIntent pi= PendingIntent.getBroadcast(MainActivity.this, i, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + 60000 * i,pendingIntent);


            if(currentInfo.getTakeTime().get(Calendar.HOUR)<Calendar.getInstance().get(Calendar.HOUR)||(currentInfo.getTakeTime().get(Calendar.HOUR)==Calendar.getInstance().get(Calendar.HOUR)
                    &&currentInfo.getTakeTime().get(Calendar.MINUTE)<Calendar.getInstance().get(Calendar.MINUTE)))
            {

                //alarmManager.cancel(pi);
                set = true;
            }
            else {
                alarmManager.set(AlarmManager.RTC, currentInfo.getTakeTime().getTimeInMillis(), pi);
                intentArray.add(pi);
            }


            if(currentInfo.getSecondReminder()&&!set) {
                Intent intent2 = new Intent(MainActivity.this, SecondReceiver.class);
                PendingIntent pi2 = PendingIntent.getBroadcast(MainActivity.this, infos.size()+i, intent2, 0);

                if (currentInfo.getLength().getValue() == 0) {
                    alarmManager.set(AlarmManager.RTC, currentInfo.getTakeTime().getTimeInMillis() + 600000, pi2);
                }
                else
                {
                    alarmManager.set(AlarmManager.RTC, currentInfo.getTakeTime().getTimeInMillis() + 900000*currentInfo.getLength().getValue(), pi2);
                }
                secondArray.add(pi2);
            }


        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String date1 = "Medicine Name: Aspirin"+"\n"+"Status: Taken in time";
        String date2 = "Medicine Name: Chocolate"+"\n" +
                "+Status: Not taken in time";
        editor.putString("20141201",date1);
        editor.putString("20140929",date2);
        editor.commit();
        //editor.putString(KEY_ET_DATA,etData.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
