package com.example.rachel.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Rachel on 10/29/15.
 */
public class Receiver extends BroadcastReceiver {

    private final String LOGTAG = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "Recieved");
        Bundle extras = intent.getExtras();

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(extras.getString("desc"));
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.icon)
//                        .setContentTitle("My Reminder");
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        context,
//                        0,
//                        notificationIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        int mNotificationId = 001;
//        NotificationManager mNotifyMgr =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}