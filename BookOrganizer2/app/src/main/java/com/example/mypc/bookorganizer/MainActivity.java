package com.example.mypc.bookorganizer;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import java.sql.Time;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements login.OnLogFromActivityListener,WelcomeFragment.OnLogoutListener ,
        TimePickerDialog.OnTimeSetListener, BooksFragment.BooksFragmentListener, ScheduleDialog.ScheduleDialogListener,
        UserScheduleFragment.UserScheduleListener,adminFragment.adminOnLogoutListener{

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefConfig = new PrefConfig(this);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        if(findViewById(R.id.fagmnt_container)!= null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }

            if(prefConfig.readLoginStatus())
            {

                getFragmentManager().beginTransaction().add(R.id.fagmnt_container, new WelcomeFragment()).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fagmnt_container,new login()).commit();
            }

        }
    }

    @Override
    public void preformregister() {
        addFragment(new registration(), "reg");
        /* getFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new registration()).addToBackStack(null).commit();*/
    }

    @Override
    public void preformlogin(String name,String UserName) {
        prefConfig.writeName(name);
        prefConfig.writeUserName(UserName);
        getFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new WelcomeFragment()).addToBackStack(null).commit();
        notfy();
    }



    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("");
        prefConfig.writeUserName("");
        getSupportFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new login()).addToBackStack(null).commit();
    }


    @Override
    public void setAlarm() {
        addalarFragment( new AlarmFragment(), "alar");
       /* getSupportFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new AlarmFragment()).addToBackStack(null).commit();*/
    }

    @Override
    public void showBooks() {
        addFragment(new BooksFragment(), "book_f");
    }

    @Override
    public void showSchedules() {
        addFragment(new UserScheduleFragment(), "user_s_f");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time time = new Time(hourOfDay,minute,00);
        Call<User> call= apiInterface.setAlarm(prefConfig.readUserName(),time);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("Alarmset"))
                {
                    MainActivity.prefConfig.displayToast("Alarm set sucsse...");
                    getTime(prefConfig.readUserName());

                }
                else if (response.body().getResponse().equals("error"))
                {
                    MainActivity.prefConfig.displayToast("something went wrong...");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    public void showScheduleDialog(Book book) {
        ScheduleDialog.newInstance(book)
                .show(getFragmentManager(), "schedule_dialog");
    }


    @Override
    public void openSchedule(Schedule schedule) {
        addFragment(ScheduleFragment.newInstance(schedule), "schedule_f");
    }

    private void addFragment(Fragment fragment, String tagF){
        Fragment curF = getFragmentManager().findFragmentByTag(tagF);

        if (curF != null && curF.isVisible()) {
            return;
        }

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fagmnt_container, fragment, tagF)
                .addToBackStack(null)
                .commit();

    }
    private void addalarFragment(AlarmFragment fragment, String tagF){
        Fragment curF = getFragmentManager().findFragmentByTag(tagF);

        if (curF != null && curF.isVisible()) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fagmnt_container, fragment, tagF)
                .addToBackStack(null)
                .commit();

    }
    public static void notfy(){
        getTime(prefConfig.readUserName());
        prefConfig.sendNotification(prefConfig.readAlarmTime());
       // prefConfig.displayToast("Yes Im working"+ prefConfig.readAlarmTime());
    }

    public static void getTime (String username){
        Call<String> call=  MainActivity.apiInterface.getTime(username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                MainActivity.prefConfig.writeAlarmTime(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    @Override
    public void preformAdminlogin() {
        getFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new adminFragment()).addToBackStack(null).commit();
    }
    @Override
    public void adminlogoutPerformed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fagmnt_container,
                new login()).addToBackStack(null).commit();
    }

}
