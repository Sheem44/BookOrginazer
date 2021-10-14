package com.example.mypc.bookorganizer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class adminFragment extends Fragment {
  private Button addbtn , updatebtn , deletbtn,adminLogOut;

   adminOnLogoutListener adminlogoutPerformed;

    public  interface adminOnLogoutListener{
        public  void adminlogoutPerformed();}


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);
        addbtn = view.findViewById(R.id.addBookbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1= getActivity().getLayoutInflater();
                View addView= inflater1.inflate(R.layout.add_book_dailog,null);
                final EditText bookName= addView.findViewById(R.id.book_name);
                final EditText NofPages= addView.findViewById(R.id.noOfpages);
                final EditText bookLink= addView.findViewById(R.id.book_link);
                Button add= addView.findViewById(R.id.add_btn);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBook(bookName,NofPages,bookLink);
                        bookName.setText("");
                        NofPages.setText("");
                        bookLink.setText("");
                    }
                });
                     builder.setView(addView);
                  AlertDialog dialog= builder.create();
                   dialog.show();


            }
        });
        updatebtn=view.findViewById(R.id.updateBookbtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1= getActivity().getLayoutInflater();
                View updateView= inflater1.inflate(R.layout.update_book_dailog,null);
                final EditText bookName= updateView.findViewById(R.id.updatebook_name);
                final EditText NofPages= updateView.findViewById(R.id.updatenoOfpages);
                final EditText bookLink= updateView.findViewById(R.id.updatebook_link);

                Button update= updateView.findViewById(R.id.update_btn);
               update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            updateBook(bookName,NofPages,bookLink);
                        }catch (Exception e){MainActivity.prefConfig.displayToast("wrong");}

                        bookName.setText("");
                        NofPages.setText("");
                        bookLink.setText("");
                    }
                });
                builder.setView(updateView);
                AlertDialog dialog= builder.create();
                dialog.show();

            }
        });
        deletbtn=view.findViewById(R.id.deletBookbtn);
        deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1= getActivity().getLayoutInflater();
                View deleteView= inflater1.inflate(R.layout.delete_book_dailog,null);
                final EditText bookName= deleteView.findViewById(R.id.deletbook_name);
                Button delete = deleteView.findViewById(R.id.delete_btn);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteBook(bookName);
                        bookName.setText("");
                    }
                });
                builder.setView(deleteView);
                AlertDialog dialog= builder.create();
                dialog.show();

            }
        });
        adminLogOut= view.findViewById(R.id.admin_logout_btn);
        adminLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminlogoutPerformed.adminlogoutPerformed();
            }
        });

        return view;
    }

    public void addBook(EditText name, EditText noOfPages, EditText bookLink){
        int NoPages;
         String book_name= name.getText().toString();
         String noOfPage= noOfPages.getText().toString();
         if (noOfPage.equals(""))
         {
             NoPages=0 ;
         }
         else{
          NoPages= Integer.parseInt(noOfPage);}
         String book_link= bookLink.getText().toString();
         Call<Admin> call= MainActivity.apiInterface.addBook(book_name,NoPages,book_link);
         call.enqueue(new Callback<Admin>() {
             @Override
             public void onResponse(Call<Admin> call, Response<Admin> response) {
                 if(response.body().getResponse().equals("exist")){
                     MainActivity.prefConfig.displayToast("Book already exist =)");
                 }
                 else if (response.body().getResponse().equals("ok"))
                 {
                     MainActivity.prefConfig.displayToast("Book is added =)");
                 }
                 else if (response.body().getResponse().equals("error"))
                 {
                     MainActivity.prefConfig.displayToast("Something went wrong XD");
                 }
                 else if (response.body().getResponse().equals("NoValues"))
                 {
                     MainActivity.prefConfig.displayToast("Please insert valid values");
                 }

             }

             @Override
             public void onFailure(Call<Admin> call, Throwable t) {

             }
         });
    }
    public void updateBook(EditText name, EditText noOfPages, EditText bookLink){
        String book_name= name.getText().toString();
        String noOfPage= noOfPages.getText().toString();
        int NoPages;
        if (noOfPage.matches(""))
            NoPages=0;
        else
         NoPages= Integer.parseInt(noOfPage);
        String book_link= bookLink.getText().toString();
        Call<Admin> call= MainActivity.apiInterface.updateBook(book_name,NoPages,book_link);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
               if (response.body().getResponse().equals("numerror")|| response.body().getResponse().equals("linkerror"))
                {
                    MainActivity.prefConfig.displayToast("Something went wrong XD");
                }
                else if (response.body().getResponse().equals("ok"))
                {
                    MainActivity.prefConfig.displayToast("Book Updated =)");
                }
               else if (response.body().getResponse().equals("noBook"))
               {
                   MainActivity.prefConfig.displayToast("no Book with that name");
               }
               else if (response.body().getResponse().equals("NoValues"))
               {
                   MainActivity.prefConfig.displayToast("Please insert values to update book");
               }

            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

            }
        });
    }
    public void deleteBook(EditText name){
        String book_name= name.getText().toString();
        Call<Admin> call= MainActivity.apiInterface.deletBook(book_name);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.body().getResponse().equals("ok"))
                {
                    MainActivity.prefConfig.displayToast("Book deleted =)");
                }
                else if (response.body().getResponse().equals("error"))
                {
                    MainActivity.prefConfig.displayToast("Something went wrong");
                }
                else if (response.body().getResponse().equals("notexist"))
                {
                    MainActivity.prefConfig.displayToast("this book is not exist");
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity =(Activity) context;
        adminlogoutPerformed= (adminOnLogoutListener) activity;
    }
}


