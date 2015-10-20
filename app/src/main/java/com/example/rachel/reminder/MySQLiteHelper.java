package com.example.rachel.reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rachel on 10/17/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TASK = "task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_FREQUENCY_NUM = "frequency_num";
    public static final String COLUMN_FREQUENCY_TYPE= "frequency_type";
    public static final String COLUMN_START_HOUR = "start_hour";
    public static final String COLUMN_START_MINUTE = "start_minute";
    public static final String COLUMN_TIME_OFF_START_HOUR = "time_off_start_hour";
    public static final String COLUMN_TIME_OFF_START_MINUTE = "time_off_start_minute";
    public static final String COLUMN_TIME_OFF_STOP_HOUR = "time_off_stop_hour";
    public static final String COLUMN_TIME_OFF_STOP_MINUTE = "time_off_stop_minute";
    public static final String COLUMN_DATE = "date";

    private static final String TASK_DB = "task.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TASK_DB = "create table "
            + TABLE_TASK + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DESCRIPTION
            + " text, "+COLUMN_FREQUENCY_NUM +" integer,"+COLUMN_FREQUENCY_TYPE+" text, "+COLUMN_START_HOUR+" integer, "+COLUMN_START_MINUTE+
            " integer,"+COLUMN_TIME_OFF_START_HOUR+" integer, "+COLUMN_TIME_OFF_START_MINUTE+" integer, "+COLUMN_TIME_OFF_STOP_HOUR+" integer, "+
            COLUMN_TIME_OFF_STOP_MINUTE+" integer, "+COLUMN_DATE+" numeric );";


    public MySQLiteHelper(Context context) {
        super(context, TASK_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TASK_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }
}
