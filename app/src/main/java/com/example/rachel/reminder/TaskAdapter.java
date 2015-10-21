package com.example.rachel.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rachel on 10/20/15.
 */
public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;
    private TaskDataSource taskDataSource;

    public TaskAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.tasks = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.task_item, parent, false);
        TextView description = (TextView) rowView.findViewById(R.id.task_item_description);
        TextView frequency = (TextView) rowView.findViewById(R.id.task_item_frequency);
        final Task task = tasks.get(position);

        description.setText(task.getDescription());
        frequency.setText("every "+task.getFrequencyNum()+" "+task.getFrequencyType().name());

        return rowView;
    }
}
