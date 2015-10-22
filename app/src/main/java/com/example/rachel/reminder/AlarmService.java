package com.example.rachel.reminder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Rachel on 10/22/15.
 */

public class AlarmService extends Service {

    public static String LOGTAG = "ALARM_SERVICE";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
//        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        alarmIntent.putExtras(intent);
//        getApplication().startActivity(alarmIntent);
//
//        AlarmManagerHelper.setAlarms(this);

        return super.onStartCommand(intent, flags, startId);
    }
}
