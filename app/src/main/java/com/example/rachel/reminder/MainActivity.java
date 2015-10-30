package com.example.rachel.reminder;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOGTAG = "MAIN_ACTIVITY";
    private TaskDataSource taskDataSource = MyApplication.getTaskDataSource();
    private List<Task> tasks = taskDataSource.getAllTasks();
    private ListView taskList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tasks = taskDataSource.getAllTasks();
        taskList = (ListView) findViewById(R.id.list);

        TaskAdapter adapter = new TaskAdapter(this, R.layout.task_item, tasks);
        taskList.setAdapter(adapter);
        registerForContextMenu(taskList);
        context = getApplicationContext();

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                final Task task = tasks.get(position);
                editTask(task);

            }
        });


    }

    public void editTask(Task task){
        Intent intent = new Intent(context, EditTaskItemActivity.class);
        intent.putExtra("description", task.getDescription());
        intent.putExtra("frequencyNum", task.getFrequencyNum());
        intent.putExtra("frequencyType", task.getFrequencyType().name());
        intent.putExtra("startHour", task.getStartHour());
        intent.putExtra("startMinute", task.getStartMinute());
        intent.putExtra("timeOffStartHour", task.getTimeOffStartHour());
        intent.putExtra("timeOffStartMinute", task.getTimeOffStartMinute());
        intent.putExtra("timeOffStopHour", task.getTimeOffStopHour());
        intent.putExtra("timeOffStopMinute", task.getTimeOffStopMinute());
        intent.putExtra("date", task.getDate().getTimeInMillis());
        intent.putExtra("id", task.getId());
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(tasks.get(info.position).getDescription());
            String[] menuItems = getResources().getStringArray(R.array.taskClickMenuArray);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
       // String[] menuItems = getResources().getStringArray(R.array.taskClickMenuArray);
       // String menuItemName = menuItems[menuItemIndex];
        Task task = tasks.get(info.position);
        if(menuItemIndex == 0){
            editTask(task);
        }
        else{
            taskDataSource.deleteTask(task);
            Toast.makeText(this, "task deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add) {
            Intent intent = new Intent(this, CreateNewTaskActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}