package com.example.mypc.bookorganizer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 *
 */

public class Book implements Parcelable{

    @SerializedName("book_name")
    private String name;
    @SerializedName("number_of_pages")
    private int pages;
    @SerializedName("book_link")
    private String link;

    public Book() {
    }

    public Book(String name, int pages, String link) {
        this.name = name;
        this.pages = pages;
        this.link = link;
    }

    //region parc
    protected Book(Parcel in) {
        name = in.readString();
        pages = in.readInt();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(pages);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    //endregion

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    //endregion
}
