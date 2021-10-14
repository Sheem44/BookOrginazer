package com.example.mypc.bookorganizer;

import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    // region fields
    private Schedule schedule ;
    private List<ScheduleInfo> list;
    private SimpleDateFormat simpleDateFormat;
    private Call<String> call;
    private ProgressBar bar;
    private TextView txtBookPages;
    private int av;
    // endregion

    public static ScheduleFragment newInstance(Schedule schedule) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConst.SCHEDULE, schedule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schedule = getArguments().getParcelable(AppConst.SCHEDULE);
        }
        simpleDateFormat = new SimpleDateFormat("EE", Locale.ENGLISH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_schedule, container, false);
        bar = view.findViewById(R.id.bar);
        final ListView listView = view.findViewById(R.id.listView);
        list = new ArrayList<>();
        TextView txtBookName = view.findViewById(R.id.txtBookName);
        txtBookPages = view.findViewById(R.id.txtBookPages);
        av = schedule.getPages() / schedule.getDays();

        ((TextView)view.findViewById(R.id.txtDate)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView)view.findViewById(R.id.txtPages)).setTextColor(getResources().getColor(R.color.colorPrimary));

        txtBookName.setText(schedule.getName());
        txtBookName.setPaintFlags(txtBookName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        int pages;
       if (schedule.getReadPages()==1)
       {
           pages = 1;
       }
       else {
           pages= schedule.getReadPages()+ av-1;
       }
        txtBookPages.setText(schedule.getPages() + " Pages in "
                + schedule.getDays() + " Days " +
                "\n " + av + " Pages in a day \n last Page read " + pages );

        listView.setAdapter(new scheduleInfoAdpter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               readPages(list.get(position).getFrom());

            }
        });
        return view;
    }

    private class scheduleInfoAdpter extends BaseAdapter
    {

        public scheduleInfoAdpter( ) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            int re = schedule.getPages() % schedule.getDays();

            for(int i = 0; i < schedule.getDays(); i++){
                ScheduleInfo s = new ScheduleInfo();
                cal.add(Calendar.DAY_OF_MONTH, i);
                s.setDate(cal.getTimeInMillis());
                int from = i * av;
                s.setFrom(from + 1);
                s.setTo(from + av);
                list.add(s);
            }
            Log.e("tag_", "  re " + re);
            if(re > 0){
                list.get(list.size()-1).setTo(list.get(list.size()-1).getTo()+re);
            }}

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();

            if(convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_schedule_info, parent, false);

                viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
                viewHolder.txtPages = convertView.findViewById(R.id.txtPages);

                convertView.setTag(viewHolder);

            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            Date date = new Date(list.get(position).getDate());

            viewHolder.txtDate.setText(String.valueOf(position + 1)+ "-     " + simpleDateFormat.format(date));
            viewHolder.txtPages.setText("form " +list.get(position).getFrom() + "....to " +
            list.get(position).getTo());

            return convertView;
        }

        private class ViewHolder{
            private TextView txtDate, txtPages;
        }

    }

    private void readPages(int readPages){
        if(call != null)
            call.cancel();

        bar.setVisibility(View.VISIBLE);

        call = MainActivity.apiInterface.readPages(MainActivity.prefConfig.readUserName(), schedule.getName(),
                readPages);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(getActivity() == null || !isAdded())
                    return;

                bar.setVisibility(View.GONE);

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getActivity() , getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body().equals("Pages Read Successfully!")){

                    txtBookPages.setText(schedule.getPages() + " Pages in "
                            + schedule.getDays() + " Days " +
                            "\n " + av + " Pages in a day \n last Page read " + schedule.getReadPages() );

                    Toast.makeText(getActivity() , response.body(),
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity() , response.body(),
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(getActivity() == null || !isAdded())
                    return;

                bar.setVisibility(View.GONE);

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This book Have not electronic version ",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
