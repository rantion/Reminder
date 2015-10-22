package com.example.rachel.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Rachel on 10/22/15.
 */

public class AlarmManagerHelper extends BroadcastReceiver {
    public static TaskDataSource taskDataSource = MyApplication.getTaskDataSource();

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {
        cancelAlarms(context);
        List<Task> tasks = taskDataSource.getAllTasks();

        for (Task task : tasks) {
                PendingIntent pIntent = createPendingIntent(context, task);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, task.getStartHour());
                calendar.set(Calendar.MINUTE, task.getStartMinute());
                calendar.set(Calendar.SECOND, 00);

                //Find next time to set
                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                boolean alarmSet = false;

                //First check if it's later in the week
//                for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
//                    if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
//                            !(dayOfWeek == nowDay && alarm.timeHour < nowHour) &&
//                            !(dayOfWeek == nowDay && alarm.timeHour == nowHour && alarm.timeMinute <= nowMinute)) {
//                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
//
//                        setAlarm(context, calendar, pIntent);
//                        alarmSet = true;
//                        break;
//                    }
//                }
//
//                //Else check if it's earlier in the week
//                if (!alarmSet) {
//                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
//                        if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay && alarm.repeatWeekly) {
//                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
//                            calendar.add(Calendar.WEEK_OF_YEAR, 1);
//
//                            setAlarm(context, calendar, pIntent);
//                            alarmSet = true;
//                            break;
//                        }
//                    }
//                }
            }

    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    public static void cancelAlarms(Context context) {

        List<Task> tasks =  taskDataSource.getAllTasks();

        if (tasks != null) {
            for (Task task : tasks) {
                    PendingIntent pIntent = createPendingIntent(context, task);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
            }
        }

    }

    private static PendingIntent createPendingIntent(Context context, Task task) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra("ID", task.getId());
        intent.putExtra("Description", task.getDescription());
        intent.putExtra("StartHour", task.getStartHour());
        intent.putExtra("StartMinute", task.getStartMinute());
        //  intent.putExtra(TONE, model.alarmTone);

        return PendingIntent.getService(context, (int) task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}