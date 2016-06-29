package com.example.architecture.bad.myfigurecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spawn on 25/06/16.
 */
public class ItemFigureDetail implements Parcelable {

    private String id;
    private String name;
    private String releaseDate;
    private String price;
    private String wishability;
    private String score;
    private String number;
    private String category;

    public ItemFigureDetail(String id, String name, String releaseDate, String score, String number, String category) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.score = score;
        this.number = number;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getScore() {
        return score;
    }

    public String getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }

    protected ItemFigureDetail(Parcel in) {
        id = in.readString();
        name = in.readString();
        releaseDate = in.readString();
        score = in.readString();
        number = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeString(score);
        dest.writeString(number);
        dest.writeString(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ItemFigureDetail> CREATOR = new Parcelable.Creator<ItemFigureDetail>() {
        @Override
        public ItemFigureDetail createFromParcel(Parcel in) {
            return new ItemFigureDetail(in);
        }

        @Override
        public ItemFigureDetail[] newArray(int size) {
            return new ItemFigureDetail[size];
        }
    };
}