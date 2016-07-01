package com.example.architecture.bad.myfigurecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spawn on 01/07/16.
 */
public class GalleryFigure implements Parcelable {

    private String id;
    private String author;
    private String date;
    private String url;

    public GalleryFigure(String id, String author, String date, String url) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "GalleryFigure{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", url='" + url + '\'' +
                '}';
    }


    protected GalleryFigure(Parcel in) {
        id = in.readString();
        author = in.readString();
        date = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GalleryFigure> CREATOR = new Parcelable.Creator<GalleryFigure>() {
        @Override
        public GalleryFigure createFromParcel(Parcel in) {
            return new GalleryFigure(in);
        }

        @Override
        public GalleryFigure[] newArray(int size) {
            return new GalleryFigure[size];
        }
    };
}
