package com.example.rachel.reminder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Rachel on 10/20/15.
 */
public class ViewTaskItemActivity extends Activity {
    private final String LOGTAG = "VIEW_TASK_ITEM_ACTIVITY";
    private long id;
    private String description;
    private int frequencyNum;
    private FrequencyType frequencyType;
    private int startHour, startMinute;
    private int timeOffStartHour, timeOffStartMinute;
    private int timeOffStopHour, timeOffStopMinute;
    private Calendar date;
    private TextView descript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_item);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id");
        description = extras.getString("description");
        frequencyNum = extras.getInt("frequencyNum");
        frequencyType = FrequencyType.valueOf(extras.getString("frequencyType"));
        startHour = extras.getInt("startHour");
        startMinute = extras.getInt("startMinute");
        timeOffStartHour = extras.getInt("timeOffStartHour");
        timeOffStopMinute = extras.getInt("timeOffStopMinute");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(extras.getLong("date"));
        date = calendar;
        descript = (TextView) findViewById(R.id.view_task_description);
        descript.setText("words");
    }
}
