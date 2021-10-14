package com.example.mypc.bookorganizer;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserScheduleFragment extends Fragment {

    //region fields
    private UserScheduleListener mListener;
    private List<Schedule> list;
    private SchedulesAdapter adapter;
    private Context context;
    private TextView txtBar;
    private ProgressBar bar, barRemove;
    private View layoutLoad;
    private SwipeRefreshLayout refreshLayout;
    private Call<List<Schedule>> callGetSchedules;
    private Call<String> callDeleteSchedule;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_schedule, container, false);
        layoutLoad = view.findViewById(R.id.layoutLoad);
        txtBar = view.findViewById(R.id.txtBar);
        bar = view.findViewById(R.id.bar);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        final GridView gridView = view.findViewById(R.id.gridView);
        layoutLoad.setVisibility(View.VISIBLE);
        barRemove = view.findViewById(R.id.barRemove);

        adapter = new SchedulesAdapter();
        gridView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSchedules();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.openSchedule(list.get(position));
            }

        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getResources().getString(R.string.notice));
                builder.setMessage(getResources().getString(R.string.remove_alert));

                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSchedule(position);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (gridView.getChildAt(0) != null) {
                    refreshLayout.setEnabled(gridView.getFirstVisiblePosition() == 0 && gridView.getChildAt(0).getTop() == 0);
                }
            }
        });

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserScheduleListener) {
            mListener = (UserScheduleListener) context;
            this.context = context;
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

    public interface UserScheduleListener {
        void openSchedule(Schedule schedule);
    }

    class SchedulesAdapter extends BaseAdapter
    {

        public SchedulesAdapter ( )
        {
            list = new ArrayList<>();
            getSchedules();
        }

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
                        .inflate(R.layout.row_sechdule, parent, false);

                viewHolder.txtSchedule = convertView.findViewById(R.id.txtSchedule);
                viewHolder.txtName = convertView.findViewById(R.id.txtTitle);
                viewHolder.txtName.setPaintFlags(viewHolder.txtName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            int s = list.get(position).getPages() / list.get(position).getDays();
            viewHolder.txtSchedule.setText(list.get(position).getPages() + " Pages in "
                    + list.get(position).getDays() + " Days " +
                    "\n " + s + " Pages in a day");

            viewHolder.txtName.setText(list.get(position).getName());
            return convertView;
        }

        private class ViewHolder{
            private TextView txtSchedule, txtName;
        }

    }

    private void getSchedules(){
        if(callGetSchedules != null)
            callGetSchedules.cancel();

        callGetSchedules = MainActivity.apiInterface.getSchedules(MainActivity.prefConfig.readUserName());
        callGetSchedules.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(context == null || !isAdded())
                    return;

                refreshLayout.setRefreshing(false);
                bar.setVisibility(View.GONE);

                if (!response.isSuccessful() ) {
                    if(layoutLoad.getVisibility() != View.VISIBLE)
                        layoutLoad.setVisibility(View.VISIBLE);
                    Toast.makeText(context , context.getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.err_msg_something_wrong));
                    return;
                }

                if(response.body() == null){
                    if(layoutLoad.getVisibility() != View.VISIBLE)
                        layoutLoad.setVisibility(View.VISIBLE);
                    Toast.makeText(context , context.getResources().getString(R.string.no_schedules_found),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.no_schedules_found));
                    return;
                }

                layoutLoad.setVisibility(View.GONE);
                list = response.body();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                if(context == null || !isAdded())
                    return;

                refreshLayout.setRefreshing(false);
                bar.setVisibility(View.GONE);
                layoutLoad.setVisibility(View.VISIBLE);

                if (t instanceof IOException) {
                    Toast.makeText(context, context.getResources().getString(R.string.err_msg_no_connection),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.no_schedules_found));
                } else {
                    Toast.makeText(context, "Choose a book and come again",
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText("No schedules yet...");
                }
            }
        });
    }

    private void deleteSchedule(final int position) {
        if (callDeleteSchedule != null)
            callDeleteSchedule.cancel();

        barRemove.setVisibility(View.VISIBLE);
        callDeleteSchedule = MainActivity.apiInterface.deleteSchedule(MainActivity.prefConfig.readUserName(),
                list.get(position).getName());
        callDeleteSchedule.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (getActivity() == null || !isAdded())
                    return;
                barRemove.setVisibility(View.GONE);

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(context, context.getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body().equals("Schedule Deleted Successfully!")) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, response.body(),
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (getActivity() == null || !isAdded())
                    return;

                barRemove.setVisibility(View.GONE);

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.err_msg_no_connection),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
