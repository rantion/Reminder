package com.example.rachel.reminder;

import android.view.View;

/**
 * Created by Rachel on 10/28/15.
 */
public interface ChangeTask {

    public void setDate(View view);

    public void setStartTime(View view);

    public void setTimeOffStart(View view);

    public void setTimeOffStop(View view);

    public void updateLabel();
}