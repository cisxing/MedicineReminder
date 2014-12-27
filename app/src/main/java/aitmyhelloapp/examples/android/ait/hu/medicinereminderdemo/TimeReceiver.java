package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by xinyunxing on 12/11/2014.
 */
public class TimeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service1 = new Intent(context, AlarmService.class);
        service1.putExtra("medicine_name",intent.getStringExtra("medicine_name"));
        context.startService(service1);
    }
}
