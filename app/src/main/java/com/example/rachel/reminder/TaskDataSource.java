package com.example.rachel.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Rachel on 10/17/15.
 */
public class TaskDataSource {
    private final String LOGTAG = "TASK_DATA_SOURCE";
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DESCRIPTION , MySQLiteHelper.COLUMN_FREQUENCY_NUM, MySQLiteHelper.COLUMN_FREQUENCY_TYPE,
            MySQLiteHelper.COLUMN_START_HOUR, MySQLiteHelper.COLUMN_START_MINUTE,MySQLiteHelper.COLUMN_TIME_OFF_START_HOUR,
            MySQLiteHelper.COLUMN_TIME_OFF_START_MINUTE,MySQLiteHelper.COLUMN_TIME_OFF_STOP_HOUR, MySQLiteHelper.COLUMN_TIME_OFF_STOP_MINUTE,
            MySQLiteHelper.COLUMN_DATE
    };

    public TaskDataSource(Context context) {
        Log.d(LOGTAG, "being created");
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String description, int frequencyNum, String frequencyType, int startHour, int startMinute, int timeOffStartHour,
                           int timeOffStartMinute, int timeOffStopHour, int timeOffStopMinute, long date) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION,description);
        values.put(MySQLiteHelper.COLUMN_FREQUENCY_NUM, frequencyNum);
        values.put(MySQLiteHelper.COLUMN_FREQUENCY_TYPE, frequencyType);
        values.put(MySQLiteHelper.COLUMN_START_HOUR, startHour);
        values.put(MySQLiteHelper.COLUMN_START_MINUTE, startMinute);
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_START_HOUR, timeOffStartHour);
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_START_MINUTE,timeOffStartMinute);
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_STOP_HOUR, timeOffStopHour);
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_STOP_MINUTE, timeOffStopMinute);
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        long insertId = database.insert(MySQLiteHelper.TABLE_TASK, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        Log.d(LOGTAG, "Task created with id: " + newTask.getId());
        return newTask;
    }

    public void updateTask(Task task){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION,task.getDescription());
        values.put(MySQLiteHelper.COLUMN_FREQUENCY_NUM, task.getFrequencyNum());
        values.put(MySQLiteHelper.COLUMN_FREQUENCY_TYPE, task.getFrequencyType().name());
        values.put(MySQLiteHelper.COLUMN_START_HOUR, task.getStartHour());
        values.put(MySQLiteHelper.COLUMN_START_MINUTE, task.getStartMinute());
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_START_HOUR, task.getTimeOffStartHour());
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_START_MINUTE, task.getTimeOffStartMinute());
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_STOP_HOUR, task.getTimeOffStopHour());
        values.put(MySQLiteHelper.COLUMN_TIME_OFF_STOP_MINUTE, task.getTimeOffStopMinute());
        values.put(MySQLiteHelper.COLUMN_DATE, task.getDate().getTimeInMillis());
        database.update(MySQLiteHelper.TABLE_TASK, values, MySQLiteHelper.COLUMN_ID + "=" + task.getId(), null);
        Log.d(LOGTAG, "Task updated with id: " + task.getId());

    }

    public void deleteTask(Task task) {
        long id = task.getId();
        Log.d(LOGTAG, "Task deleted with id: " + task.getId());
        database.delete(MySQLiteHelper.TABLE_TASK, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }


    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASK,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public Task getTaskById(long id){
        Task task = null;
        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_TASK + " where " +
                MySQLiteHelper.COLUMN_ID + "=" + id + "", null);
        if(cursor!=null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                task = cursorToTask(cursor);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return task;
    }

    private Task cursorToTask(Cursor cursor) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(10));
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setDescription(cursor.getString(1));
        task.setFrequencyNum(cursor.getInt(2));
        task.setFrequencyType(FrequencyType.valueOf(cursor.getString(3).toUpperCase()));
        task.setStartHour(cursor.getInt(4));
        task.setStartMinute(cursor.getInt(5));
        task.setTimeOffStartHour(cursor.getInt(6));
        task.setTimeOffStartMinute(cursor.getInt(7));
        task.setTimeOffStopHour(cursor.getInt(8));
        task.setTimeOffStopMinute(cursor.getInt(9));
        task.setDate(calendar);
        return task;
    }
}
