package com.example.mypc.bookorganizer;

import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("response")
    private String Response;
    public String getResponse() {
        return Response;
    }
}
