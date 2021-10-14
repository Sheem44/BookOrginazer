package com.example.mypc.bookorganizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class registration extends android.app.Fragment {
    private EditText Name, UserName,UserPassword;
    private Button Registerbtn;

    public registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registration, container, false);
        Name= view.findViewById(R.id.text_name);
        UserName = view.findViewById(R.id.text_username);
        UserPassword = view.findViewById(R.id.text_password);
        Registerbtn = view.findViewById(R.id.register_btn);
        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
        return view;
    }
    public void performRegistration()
    {
        String name = Name.getText().toString();
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();
        Call<User> call= MainActivity.apiInterface.preformRegistration(name,username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok"))
                {
                    MainActivity.prefConfig.displayToast("Registration success...");
                }
                else if (response.body().getResponse().equals("exit"))
                {
                    MainActivity.prefConfig.displayToast("User already exist...");
                }
                else if (response.body().getResponse().equals("error"))
                {
                    MainActivity.prefConfig.displayToast("Something went wrong...");
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
        Name.setText("");
        UserName.setText("");
        UserPassword.setText("");

    }
}
