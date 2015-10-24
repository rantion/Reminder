package com.example.rachel.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Rachel on 10/20/15.
 */
public class ViewTaskItemActivity extends Activity {
    private final String LOGTAG = "VIEW_TASK_ITEM_ACTIVITY";
    private TaskDataSource taskDataSource = MyApplication.getTaskDataSource();
    private long id;
    private String description;
    private int frequencyNum;
    private String frequencyType;
    private MyTime startTime,timeOffStart, timeOffStop;
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

    public void deleteTask(View view){
        Task task = taskDataSource.getTaskById(id);
        taskDataSource.deleteTask(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void editTask(View view){
        Intent intent = new Intent(this, EditTaskItemActivity.class);
        intent.putExtra("description", description);
        intent.putExtra("frequencyNum", frequencyNum);
        intent.putExtra("frequencyType", frequencyType);
        intent.putExtra("startHour", startTime.getHour());
        intent.putExtra("startMinute", startTime.getMinute());
        intent.putExtra("timeOffStartHour", timeOffStart.getHour());
        intent.putExtra("timeOffStartMinute", timeOffStart.getMinute());
        intent.putExtra("timeOffStopHour", timeOffStop.getHour());
        intent.putExtra("timeOffStopMinute", timeOffStop.getMinute());
        intent.putExtra("date", date.getTimeInMillis());
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
