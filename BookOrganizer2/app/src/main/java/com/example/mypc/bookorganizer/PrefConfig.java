package com.example.mypc.bookorganizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;


public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;


    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_login_status), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void writeName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user), name);
        editor.commit();
    }

    public String readName() {
        return sharedPreferences.getString(context.getString(R.string.pref_user), "user");
    }

    public void displayToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void writeUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), name);
        editor.commit();
    }

    public String readUserName() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "user");
    }

    public void writeAlarmTime(String time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_alarm_time), time);
        editor.commit();
    }

    public String readAlarmTime() {
        return sharedPreferences.getString(context.getString(R.string.pref_alarm_time), "05:00:00");
    }

    public void sendNotification(String Time) {
        int hour= Integer.parseInt(Time.substring(0,2));
        int minute= Integer.parseInt(Time.substring(3,5));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            Intent intent = new Intent(context, Notification_reciver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
