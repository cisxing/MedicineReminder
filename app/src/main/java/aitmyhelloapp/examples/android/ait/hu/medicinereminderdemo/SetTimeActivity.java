package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;


public class SetTimeActivity extends Activity {

    /** Private members of the class */
    private TextView displayTime;
    private Button pickTime;
    private Calendar medicineTime;
    private int pHour;
    private int pMinute;
    /** This integer will uniquely define the dialog to be used for displaying time picker.*/
    static final int TIME_DIALOG_ID = 0;

    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplay();
                    displayToast();
                }
            };

    /** Updates the time in the TextView */
    private void updateDisplay() {
        medicineTime.set(Calendar.HOUR_OF_DAY, pHour);
        medicineTime.set(Calendar.MINUTE,pMinute);
        medicineTime.set(Calendar.SECOND, 0);
        displayTime.setText(
                new StringBuilder()
                        .append(pad(pHour)).append(":")
                        .append(pad(pMinute)));

    }

    /** Displays a notification when the time is updated */
    private void displayToast() {
        Toast.makeText(this, new StringBuilder().append("Time choosen is ").append(displayTime.getText()), Toast.LENGTH_SHORT).show();

    }

    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        medicineTime = Calendar.getInstance();
        final Spinner spinnerTime = (Spinner) findViewById(R.id.spinnerLength);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);
        final EditText etMedicineName = (EditText) findViewById(R.id.etMedicineName);
        //this is scary... Do not know what is going to happen...
        try {
            etMedicineName.setText(getIntent().getStringExtra("medicine_name"));
            spinnerTime.setSelection(getIntent().getIntExtra("medicine_length",0));

        } finally{

        }

        /** Capture our View elements */
        displayTime = (TextView) findViewById(R.id.timeDisplay);
        pickTime = (Button) findViewById(R.id.pickTime);

        /** Listener for click event of the button */
        pickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        /** Get the current time */
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);

        /** Display the current time in the TextView */
        updateDisplay();
        final CheckBox secondReminder = (CheckBox) findViewById(R.id.cb_second);

        /**copied from the other class
        //i.putExtra("medicine_name",medicineInfo.getMedicineName());
        //i.putExtra("medicine_time",medicineInfo.getTakeTime());
        //i.putExtra("medicine_length",medicineInfo.getLength().getStringValue());
        //i.putExtra("medicine_secondReminder",medicineInfo.getSecondReminder());
         **/

        try{
            secondReminder.setChecked(getIntent().getBooleanExtra("medicine_secondReminder",false));
            displayTime.setText(getIntent().getStringExtra("medicine_time"));
        }finally{

        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                MedicineInfo newS = new MedicineInfo(etMedicineName.getText().toString(),medicineTime,MedicineInfo.RepeatLength.fromInt(spinnerTime.getSelectedItemPosition()),secondReminder.isChecked());
                intentResult.putExtra(getString(R.string.key_value),
                        newS);
                setResult(RESULT_OK,intentResult);
                finish();
            }
        });
    }

    /** Create a new dialog for time picker */

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, pHour, pMinute, false);
        }
        return null;
    }
}

