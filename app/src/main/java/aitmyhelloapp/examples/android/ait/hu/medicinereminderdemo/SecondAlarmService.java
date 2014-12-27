package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class SecondAlarmService extends Service {
    private NotificationManager mManager;


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }


    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Log.d("second","onStartCalled?");
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),ConfirmationActivity.class);
        try {
            if (intent!= null) {
                Notification notification = new Notification(R.drawable.alarm2, "Second Reminder", System.currentTimeMillis());

                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, 0);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.setLatestEventInfo(this.getApplicationContext(), "Second Alarm!", "Can just dismiss", pendingNotificationIntent);

                mManager.notify(0, notification);

                Vibrator vibrator = (Vibrator) getBaseContext().getSystemService(getBaseContext().VIBRATOR_SERVICE);
                vibrator.vibrate(4000);
            }
        }finally
        {

        }
    }
}
