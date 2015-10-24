package com.example.rachel.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Rachel on 10/24/15.
 */
public class EditTaskItemActivity extends Activity {
    private final String LOGTAG = "EDIT_TASK_ITEM_ACTIVITY";
    private TaskDataSource taskDataSource = MyApplication.getTaskDataSource();
    private long id;
    private String description;
    private int frequencyNum;
    private String frequencyType;
    private MyTime startTime,timeOffStart, timeOffStop;
    private Calendar date;
    private SimpleDateFormat simpleDateFormat;
    private String sdf = "MMM/dd/yyyy";
    private ArrayAdapter adapter;
    private EditText editDescription, editFrequencyNum;
    private TextView editStartTime,editTimeOffStart, editTimeOffStop, editDate;
    private Spinner editFrequencyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_item);
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
        editDescription = (EditText) findViewById(R.id.edit_description);
        editFrequencyNum = (EditText)findViewById(R.id.edit_frequency_num);
        editStartTime = (TextView) findViewById(R.id.edit_start_time);
        editTimeOffStart = (TextView) findViewById(R.id.edit_time_off_start);
        editTimeOffStop = (TextView) findViewById(R.id.edit_time_off_stop);
        editDate = (TextView)findViewById(R.id.edit_date);

        editFrequencyType = (Spinner)findViewById(R.id.edit_frequency_type);
        adapter = ArrayAdapter.createFromResource(this, R.array.frequencyArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
//        if(editFrequencyType == null){
//            Log.d(LOGTAG, "SPINNER IS NULL?");
//        }
        editFrequencyType.setAdapter(adapter);
       // ArrayAdapter myAdap = (ArrayAdapter) editFrequencyType.getAdapter();
        int spinnerPosition = adapter.getPosition(frequencyType);

        editDescription.setText(description);
        editFrequencyNum.setText(String.valueOf(frequencyNum));
        editFrequencyType.setSelection(spinnerPosition);
        editStartTime.setText(startTime.getTime());
        editTimeOffStart.setText(timeOffStart.getTime());
        editTimeOffStop.setText(timeOffStop.getTime());
        editDate.setText(simpleDateFormat.format(date.getTime()));
    }

    public void saveTask(View view){
        Task task = taskDataSource.getTaskById(id);
        task.setDescription(editDescription.getText().toString());
        task.setFrequencyNum(Integer.parseInt(editFrequencyNum.getText().toString()));
        task.setFrequencyType(FrequencyType.valueOf(editFrequencyType.getSelectedItem().toString().toUpperCase()));
        task.setStartHour(startTime.getHour());
        task.setStartMinute(startTime.getMinute());
        task.setTimeOffStartHour(timeOffStart.getHour());
        task.setTimeOffStartMinute(timeOffStart.getMinute());
        task.setTimeOffStopHour(timeOffStop.getHour());
        task.setTimeOffStopMinute(timeOffStop.getMinute());
        task.setDate(date);
        taskDataSource.updateTask(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
