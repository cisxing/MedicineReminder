package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Calendar;


public class DialogActivity extends Activity {
    public static final String SP_DATA = "SP_DATA";

    Button yes;
    Button no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year= c.get(Calendar.YEAR);
        final String date =  new StringBuilder()
                .append(year).append(pad(month))
                .append(pad(day)).toString();

        yes = (Button) findViewById(R.id.btnYes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String status = "\n"+getIntent().getStringExtra("medicine_name")+"\n"+"Status: Taken on time";
                if(!sp.getString(date,"").equals(""))
                {
                    Log.d("date", date);
                    editor.putString(date,sp.getString(date,"")+status);
                }
                else
                {
                    Log.d("date", date);
                    editor.putString(date,status);
                }

                editor.commit();
                //write to the record
                finish();
            }
        });
        no = (Button) findViewById(R.id.btnNo);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write to the record
                //text people
                SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String status = "\n"+"Medicine name: "+getIntent().getStringExtra("medicine_name")+"\n"+"Status: Not Taken on time";
                if(!sp.getString(date,"").equals(""))
                {
                    Log.d("date", date);
                    editor.putString(date,sp.getString(date,"")+status);
                }
                else
                {
                    Log.d("date", date);
                    editor.putString(date,"Medicine name: "+getIntent().getStringExtra("medicine_name")+"\n"+"Status: Not Taken on time");
                }

                editor.commit();
                //write to the record
                //how to send an sms message
                SmsManager smsManager = SmsManager.getDefault();
                //has to use the valid phone number to send the sms
                if(!sp.getString("phone","").equals("")) {
                    smsManager.sendTextMessage(sp.getString("phone", ""), null, "Your friend is not taking her medicine! Please remind her!", null, null);
                }
                finish();
            }
        });

    }


    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dialog, menu);
        return true;
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
