package com.example.rachel.reminder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Rachel on 10/20/15.
 */
public class ViewTaskItemActivity extends Activity {
    private final String LOGTAG = "VIEW_TASK_ITEM_ACTIVITY";
    private long id;
    private String description;
    private int frequencyNum;
    private String frequencyType;
    private int startHour, startMinute;
    private MyTime startTime,timeOffStart, timeOffStop;
    private int timeOffStartHour, timeOffStartMinute;
    private int timeOffStopHour, timeOffStopMinute;
    private Calendar date;
    private SimpleDateFormat simpleDateFormat;
    private String sdf = "MMM/dd/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_item);
        simpleDateFormat = new SimpleDateFormat(sdf, Locale.US);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id");
        description = extras.getString("description");
        frequencyNum = extras.getInt("frequencyNum");
        frequencyType = extras.getString("frequencyType");
        startTime = new MyTime(extras.getInt("startHour"),extras.getInt("startMinute"));
        timeOffStart = new MyTime(extras.getInt("timeOffStartHour"),extras.getInt("timeOffStarMinute"));
        timeOffStop = new MyTime(extras.getInt("timeOffStopHour"),extras.getInt("timeOffStopMinute"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(extras.getLong("date"));
        date = calendar;
        initViews();
    }

    public void initViews(){
        TextView viewDescription = (TextView) findViewById(R.id.view_description);
        TextView viewFrequencyNum = (TextView)findViewById(R.id.view_frequency_num);
        TextView viewFrequencyType = (TextView)findViewById(R.id.view_frequency_type);
        TextView viewStartTime = (TextView) findViewById(R.id.view_start_time);
        TextView viewTimeOffStart = (TextView) findViewById(R.id.view_time_off_start);
        TextView viewTimeOffStop = (TextView) findViewById(R.id.view_time_off_stop);
        TextView viewDate = (TextView)findViewById(R.id.view_date);

        viewDescription.setText(description);
        viewFrequencyNum.setText(String.valueOf(frequencyNum));
        viewFrequencyType.setText(frequencyType);
        viewStartTime.setText(startTime.getTime());
        viewTimeOffStart.setText(timeOffStart.getTime());
        viewTimeOffStop.setText(timeOffStop.getTime());
        viewDate.setText(simpleDateFormat.format(date.getTime()));

    }
}
