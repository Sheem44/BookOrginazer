package com.example.mypc.bookorganizer;




import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.TimePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    private Button clockBtn;


    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_alarm, container, false);

        clockBtn= view.findViewById(R.id.clock_btn);
        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment clock= new TimeClock();
                clock.show(getActivity().getSupportFragmentManager(),"Time Picker");

            }});
        return view;
    }


}
