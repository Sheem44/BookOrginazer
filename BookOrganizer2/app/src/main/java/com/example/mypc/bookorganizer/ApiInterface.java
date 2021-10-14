package com.example.mypc.bookorganizer;

import java.sql.Time;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("register.php")
    Call<User> preformRegistration(@Query("name") String Name, @Query("user_name") String UserName, @Query("user_password")String UserPassword);
    @GET("login.php")
    Call<User> preformUserLogin(@Query("user_name") String UserName, @Query("user_password")String UserPassword);
    @GET("AddSchedule.php")
    Call<Schedule> addDays(@Query("user_name") String UserName, @Query("book_name")String BookName, @Query("days") int Days);
    @GET("addBook.php")
    Call<Admin> addBook(@Query("book_name")String BookName,@Query("number_of_pages")int NoPages,@Query("book_link")String BookLink);
    @GET("deletBook.php")
    Call<Admin> deletBook(@Query("book_name")String BookName);
    @GET("updateBook.php")
    Call<Admin> updateBook(@Query("book_name")String BookName,@Query("number_of_pages")int NoPages,@Query("book_link")String BookLink);
    @GET("setAlarm.php")
    Call<User> setAlarm(@Query("user_name") String UserName, @Query("alarm_Time")Time time);
    @GET("getTime.php")
    Call<String> getTime(@Query("user_name")String UserName);
    @GET("GetBooks.php")
    Call<List<Book>> getBooks();
    @GET("GetSchedules.php")
    Call<List<Schedule>> getSchedules(@Query("user_name") String UserName);
    @GET("DeleteSchedule.php")
    Call<String> deleteSchedule(@Query("user_name") String UserName, @Query("book_name")String BookName);
    @GET("ReadPages.php")
    Call<String> readPages(@Query("user_name") String UserName, @Query("book_name")String BookName, @Query("read_pages")int readPages);
    @GET("AddSchedule.php")
    Call<String> addSchedule(@Query("user_name") String UserName, @Query("book_name")String BookName, @Query("days") int Days,
                             @Query("number_of_pages")int pages);
}
