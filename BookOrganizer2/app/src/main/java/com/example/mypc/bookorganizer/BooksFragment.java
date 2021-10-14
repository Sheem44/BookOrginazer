package com.example.mypc.bookorganizer;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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


public class BooksFragment extends Fragment  {

    //region fields
    private TextView txtBar;
    private ProgressBar bar;
    private View layoutLoad;
    private List<Book> list;
    private BooksAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private String tag = "tag_BooksFragment";
    private BooksFragmentListener mListener;
    private Call<List<Book>> call;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_books, container, false);
        layoutLoad = view.findViewById(R.id.layoutLoad);
        txtBar = view.findViewById(R.id.txtBar);
        bar = view.findViewById(R.id.bar);
        final GridView gridView = view.findViewById(R.id.gridView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        layoutLoad.setVisibility(View.VISIBLE);

        adapter = new BooksAdapter ();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.showScheduleDialog(list.get(position));
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBooks();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BooksFragmentListener) {
            mListener = (BooksFragmentListener) context;
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

    public interface BooksFragmentListener {
        void showScheduleDialog(Book book);
    }


    class BooksAdapter extends BaseAdapter
    {

        public BooksAdapter ( )
        {
            list = new ArrayList<>();
            getBooks();
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
                        .inflate(R.layout.row_book, parent, false);

                viewHolder.txtTitle = convertView.findViewById(R.id.txtTitle);
                viewHolder.txtAuthor = convertView.findViewById(R.id.txtAuthor);

                convertView.setTag(viewHolder);

            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.txtTitle.setText(list.get(position).getName());
            String noPages = String.format("%d",list.get(position).getPages());
            viewHolder.txtAuthor.setText(noPages +" Pages");
            return convertView;
        }

        private class ViewHolder{
            private TextView txtTitle, txtAuthor;
        }

    }


    private void getBooks(){
        if(call != null)
            call.cancel();

        call= MainActivity.apiInterface.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(getActivity() == null || !isAdded())
                    return;

                refreshLayout.setRefreshing(false);
                bar.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    layoutLoad.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity() , getResources().getString(R.string.err_msg_something_wrong),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.err_msg_something_wrong));
                    return;
                }

                if(response.body() == null){
                    layoutLoad.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity() , getResources().getString(R.string.no_books_found),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.no_books_found));
                    return;
                }

                layoutLoad.setVisibility(View.GONE);
                list = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                if(getActivity() == null || !isAdded())
                    return;

                Log.e(tag, "onFailure");

                refreshLayout.setRefreshing(false);
                layoutLoad.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.err_msg_no_connection),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.err_msg_no_connection));
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_books_found),
                            Toast.LENGTH_SHORT).show();
                    txtBar.setText(getResources().getString(R.string.no_books_found));
                }
            }
        });
    }


}

