package com.example.rachel.reminder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Console;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Rachel on 10/17/15.
 */
public class CreateNewTaskActivity extends Activity {

    private final String LOGTAG = "CREATE_NEW_TASK";
    private TaskDataSource taskDataSource = MyApplication.getTaskDataSource();
    private Spinner frequencyTypeSpinner;
    private TextView dateSetter, startTimeSetter, timeOffStartSetter, timeOffStopSetter;
    private Calendar calendar;
    private MyTime startTime, timeOffStart, timeOffStop;
    private SimpleDateFormat simpleDateFormat;
    private String sdf = "MMM/dd/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        frequencyTypeSpinner = (Spinner)findViewById(R.id.spinner_frequency);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(sdf, Locale.US);
        dateSetter = (TextView)findViewById(R.id.set_date);
        startTimeSetter = (TextView) findViewById(R.id.set_start_time);
        timeOffStartSetter = (TextView) findViewById(R.id.set_time_off_start);
        timeOffStopSetter = (TextView) findViewById(R.id.set_time_off_stop);
        dateSetter.setText(simpleDateFormat.format(calendar.getTime()));
        timeOffStart = new MyTime(22,0);
        timeOffStop = new MyTime(7,0);
        startTime = new MyTime(calendar.get(calendar.HOUR_OF_DAY),calendar.get(calendar.MINUTE));
        startTimeSetter.setText(startTime.getTime());
        timeOffStartSetter.setText(timeOffStart.getTime());
        timeOffStopSetter.setText(timeOffStop.getTime());
        startTime = new MyTime(calendar.HOUR_OF_DAY, calendar.MINUTE);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.frequencyArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        frequencyTypeSpinner.setAdapter(adapter);
    }

    public void setDate(View view){

        new DatePickerDialog(CreateNewTaskActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            Log.d(LOGTAG,"year: "+year+" month: "+monthOfYear+" day: "+dayOfMonth);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public void setStartTime(View view){
        Calendar current = Calendar.getInstance();
        Log.d("START TIME"," HOUR: "+current.get(current.HOUR_OF_DAY)+"MINUTE:"+current.get(current.MINUTE));

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateNewTaskActivity.this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                startTime.setTime(selectedHour, selectedMinute);
                startTimeSetter.setText(startTime.getTime());
            }
        },current.get(current.HOUR_OF_DAY), current.get(current.MINUTE), false);//Yes 24 hour time
        timePickerDialog.setTitle("Select MyTime");
        timePickerDialog.show();

    }

    public void setOffStartTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateNewTaskActivity.this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeOffStart.setTime(selectedHour, selectedMinute);
                timeOffStartSetter.setText(timeOffStart.getTime());
            }
        }, timeOffStart.getHour(), timeOffStart.getMinute(), false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void setOffStopTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateNewTaskActivity.this,TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeOffStop.setTime(selectedHour, selectedMinute);
                timeOffStopSetter.setText(timeOffStop.getTime());
            }
        }, timeOffStop.getHour(), timeOffStop.getMinute(), false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void updateLabel (){
        dateSetter.setText(simpleDateFormat.format(calendar.getTime()));
    }


    public void createTask(View view){
        EditText descriptionField = (EditText)findViewById(R.id.enter_description);
        EditText frequencyNumField = (EditText)findViewById(R.id.enter_frequency_num);

        long dateMillis = calendar.getTimeInMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");
        calendar.setTimeInMillis(dateMillis);
        Log.d(LOGTAG,dateMillis +" : "+ format.format(calendar.getTime()));
        String description = descriptionField.getText().toString();
        Log.d("CREATE_NEW_TASK",frequencyNumField.getText().toString());
        int frequencyNum = Integer.parseInt(frequencyNumField.getText().toString());
        String frequencyType = frequencyTypeSpinner.getSelectedItem().toString();

        Task task = taskDataSource.createTask(description, frequencyNum,frequencyType, startTime.getHour(), startTime.getMinute(),
                timeOffStart.getHour(),timeOffStart.getMinute(),timeOffStop.getHour(),timeOffStop.getMinute(),calendar.getTimeInMillis());
     //   Task task = taskDataSource.createTask("description", 5, "minutes", 0, 0, 0, 0, 0, 0, 0);
        startAlarm(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void startAlarm(Task task) {
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);
        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("desc", task.getDescription());
        Calendar cal = Calendar.getInstance();
        cal = task.getDate();
        cal.set(Calendar.HOUR_OF_DAY, task.getStartHour());
        cal.set(Calendar.MINUTE, task.getStartMinute());
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy kk:mm");
        Log.d(LOGTAG,format.format(cal.getTime()));
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarms.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), operation) ;
    }
}
