package com.example.rachel.reminder;

import android.text.format.Time;

import java.util.Calendar;

/**
 * Created by Rachel on 10/17/15.
 */
public class Task {

    private long id;
    private String description;
    private int frequencyNum;
    private FrequencyType frequencyType;
    private int startHour, startMinute;
    private int timeOffStartHour, timeOffStartMinute;
    private int timeOffStopHour, timeOffStopMinute;
    private Calendar date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequencyNum() {
        return frequencyNum;
    }

    public void setFrequencyNum(int frequencyNum) {
        this.frequencyNum = frequencyNum;
    }

    public FrequencyType getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(FrequencyType frequencyType) {
        this.frequencyType = frequencyType;
    }

    public int getTimeOffStartHour() {
        return timeOffStartHour;
    }

    public void setTimeOffStartHour(int timeOffStartHour) {
        this.timeOffStartHour = timeOffStartHour;
    }

    public int getTimeOffStartMinute() {
        return timeOffStartMinute;
    }

    public void setTimeOffStartMinute(int timeOffStartMinute) {
        this.timeOffStartMinute = timeOffStartMinute;
    }

    public int getTimeOffStopHour() {
        return timeOffStopHour;
    }

    public void setTimeOffStopHour(int timeOffStopHour) {
        this.timeOffStopHour = timeOffStopHour;
    }

    public int getTimeOffStopMinute() {
        return timeOffStopMinute;
    }

    public void setTimeOffStopMinute(int timeOffStopMinute) {
        this.timeOffStopMinute = timeOffStopMinute;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startTimeMinute) {
        this.startMinute = startTimeMinute;
    }
}
