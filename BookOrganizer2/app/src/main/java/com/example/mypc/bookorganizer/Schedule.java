package com.example.mypc.bookorganizer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Schedule implements Parcelable{
    @SerializedName("book_name")
    private String name;
    @SerializedName("number_of_pages")
    private int pages;
    @SerializedName("days")
    private int days;
    @SerializedName("read_pages")
    private int readPages;

    public Schedule(String name, int pages, int days, int readPages) {
        this.name = name;
        this.pages = pages;
        this.days = days;
        this.readPages = readPages;
    }


    //region set get

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getReadPages() {
        return readPages;
    }

    public void setReadPages(int readPages) {
        this.readPages = readPages;
    }

    //endregion

    //region parc
    protected Schedule(Parcel in) {
        name = in.readString();
        pages = in.readInt();
        days = in.readInt();
        readPages = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(pages);
        dest.writeInt(days);
        dest.writeInt(readPages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
    //endregion
}
