package com.example.mypc.bookorganizer;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class User {
    @SerializedName("response")
    private String Response;
    @SerializedName("name")
    private String Name;
    @SerializedName("user_name")
    private String UserName;



    public String getResponse() {
        return Response;
    }
    public String getName() {
        return Name;
    }
    public String getUserName(){return UserName;}

    @SerializedName("alarm_Time")
    String alarmTime;

    public String getAlarmTime() {
        return alarmTime;
    }
}
