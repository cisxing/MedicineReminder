package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;


public class SetPhoneNumberActivity extends Activity {

    public static final String SP_DATA = "SP_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone_number);

        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final Button btnPhone = (Button) findViewById(R.id.btnPhone);
        try {
            etPhone.setText(getIntent().getStringExtra("phone"));
        } finally
        {

        }
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences(SP_DATA,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("phone_number",etPhone.getText().toString());
                editor.commit();
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_phone_number, menu);
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
