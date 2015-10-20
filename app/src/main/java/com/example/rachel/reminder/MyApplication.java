package com.example.rachel.reminder;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Rachel on 10/17/15.
 */
public class MyApplication extends Application {

    protected static TaskDataSource taskDataSource;
    private static Context context;

    public void onCreate() {
        Log.d("MYAPPLICATION", "IN ON CREATE");
        MyApplication.context = getApplicationContext();
        try {
            taskDataSource = new TaskDataSource(context);
            taskDataSource.open();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    public static TaskDataSource getTaskDataSource() {
        return taskDataSource;
    }

    public static void setTaskDataSource(TaskDataSource taskDataSource) {
        MyApplication.taskDataSource = taskDataSource;
    }
}
