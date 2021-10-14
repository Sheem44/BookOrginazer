package com.example.mypc.bookorganizer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * .
 */

public class ScheduleDialog extends DialogFragment{

    // region fields
    private Spinner spinnerDays;
    private ProgressBar bar;
    public String userName;
    private Book book ;
    private ScheduleDialogListener mListener;
    private Call<String> call;
    // endregion

    public static ScheduleDialog newInstance(Book book) {
        ScheduleDialog fragment = new ScheduleDialog();
        Bundle args = new Bundle();
        args.putParcelable(AppConst.BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(AppConst.BOOK);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_schedule, null);
        bar = view.findViewById(R.id.bar);
        spinnerDays = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getActivity().getBaseContext()
                ,R.array.Days,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapter);
        userName = MainActivity.prefConfig.readUserName();

        final AlertDialog  builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.create_schedule)
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        int days = 1;
                        String dayString = spinnerDays.getSelectedItem().toString();
                        try {
                            days = Integer.parseInt(dayString);
                        } catch(NumberFormatException ex) {
                            System.out.println("Could not parse " + ex);
                        }

                        spinnerDays.setVisibility(View.GONE);
                        bar.setVisibility(View.VISIBLE);
                        addSchedule(days);
                    }
                });
            }
        });
        return builder;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScheduleDialogListener) {
            mListener = (ScheduleDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface ScheduleDialogListener {
        void openSchedule(Schedule schedule);
    }

    private void addSchedule(final int days){
        if(call != null)
            call.cancel();

        call= MainActivity.apiInterface.addSchedule(MainActivity.prefConfig.readUserName(), book.getName(), days, book.getPages());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String > call, Response<String> response) {

                if(getActivity() == null && !isAdded())
                    return;

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getActivity() , getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }

                if(response.body().equals("Schedule Added Successfully!")){
                    Schedule schedule = new Schedule(book.getName(), book.getPages(), days, 0);
                    Toast.makeText(getActivity(), response.body(),
                            Toast.LENGTH_SHORT).show();
                    mListener.openSchedule(schedule);
                }else {
                    Toast.makeText(getActivity(), response.body(),
                            Toast.LENGTH_SHORT).show();
                }

                dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                if(getActivity() == null && !isAdded())
                    return;

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.err_msg_no_connection),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                }

                dismiss();
            }
        });
    }
}
