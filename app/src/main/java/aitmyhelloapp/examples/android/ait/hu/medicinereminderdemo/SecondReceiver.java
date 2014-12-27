package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SecondReceiver extends BroadcastReceiver {
    public SecondReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, SecondAlarmService.class);
        context.startService(service1);
    }
}
