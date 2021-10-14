package com.example.mypc.bookorganizer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//" http://192.168.1.3/bookOragnizerPHP/"
//" http://10.0.2.2/bookOragnizerPHP/"
public class ApiClient {
    public static final String BASE_URL = "http://10.0.2.2/bookOragnizerPHP/";
    public static Retrofit retrofit;

    public static Retrofit getApiClient() {

        if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return retrofit;
    }
}

