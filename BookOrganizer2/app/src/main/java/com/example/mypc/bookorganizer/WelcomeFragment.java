package com.example.mypc.bookorganizer;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends android.app.Fragment {
    private TextView nameInfo;
    private Button Logoutbtn,setAlarmbtn;
    OnLogoutListener onLogoutListener;

    public  interface OnLogoutListener{
        public void logoutPerformed();
        public void setAlarm();
        void showBooks();
        void showSchedules();
    }


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome, container, false);
        nameInfo = view.findViewById(R.id.text_name_info);
        nameInfo.setText("Hello "+MainActivity.prefConfig.readName());
        Logoutbtn= view.findViewById(R.id.logout_btn);
        Logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onLogoutListener.logoutPerformed();
            }
        });
       setAlarmbtn= view.findViewById(R.id.setAlarm_btn) ;
       setAlarmbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onLogoutListener.setAlarm();
           }
       });


       Button btnBooks = view.findViewById(R.id.btnBooks);
       Button btnSchedules = view.findViewById(R.id.btnSchedule);
       btnBooks.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onLogoutListener.showBooks();
           }
       });
       btnSchedules.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onLogoutListener.showSchedules();
           }
       });
         nameInfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 MainActivity.notfy();
             }
         });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity =(Activity) context;
        onLogoutListener= (OnLogoutListener) activity;
    }
  }

