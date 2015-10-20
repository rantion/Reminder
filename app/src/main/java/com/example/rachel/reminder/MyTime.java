package com.example.rachel.reminder;

/**
 * Created by Rachel on 10/18/15.
 */
public class MyTime {

    private int hour, minute;

    public MyTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public void setTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public String getTime(){
        String time = "";
        String min = "";
        if(minute<10){
            min = "0"+minute;
        }
        else{
            min = String.valueOf(minute);
        }
        if(hour<13){
            time = hour + ":" + min+ " am";
        }
        else{
            time = hour-12+ ":" + min+ " pm";
        }
        return time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
