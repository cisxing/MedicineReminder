package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.R;

public class ShowHistoryActivity extends Activity {

    TextView tvinfo;
    public static final String SP_DATA = "SP_DATA";
    public final String DATE_CODE = "date_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        tvinfo = (TextView) findViewById(R.id.tvInfo);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
        tvinfo.setText(sp.getString(getIntent().getStringExtra(DATE_CODE), "No medicine is taken today."));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_history, menu);
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

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


        editor.commit();
    }
}
