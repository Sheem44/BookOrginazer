package com.example.mypc.bookorganizer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends Fragment {
    private TextView regText;
    private EditText  UserName,UserPassword;
    private Button Loginbtn;
    OnLogFromActivityListener logFromActivityListener;

    public interface OnLogFromActivityListener
    {
        public void preformregister();
        public void preformlogin(String name,String UserName);
        public void preformAdminlogin();
    }


    public login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        UserName = view.findViewById(R.id.user_name);
        UserPassword = view.findViewById(R.id.user_password);
        Loginbtn = view.findViewById(R.id.login_btn);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });


        regText = view.findViewById(R.id.register_text);
        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logFromActivityListener.preformregister();

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity =(Activity) context;
        logFromActivityListener= (OnLogFromActivityListener) activity;
    }

    public void performLogin()
    {
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();
        Call<User> call= MainActivity.apiInterface.preformUserLogin(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getResponse().equals("ok"))
                {

                    logFromActivityListener.preformlogin(response.body().getName(),response.body().getUserName());
                    MainActivity.prefConfig.writeLoginStatus(true);

                }

                else if (response.body().getResponse().equals("failed"))
                {
                    MainActivity.prefConfig.displayToast(" Invalid User Name or Password...");
                }
                else if (response.body().getResponse().equals("admin"))
                {
                   logFromActivityListener.preformAdminlogin();
                }
                else if (response.body().getResponse().equals("empty"))
                {
                    MainActivity.prefConfig.displayToast(" Please insert valid values...");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        UserName.setText("");
        UserPassword.setText("");

    }




}
