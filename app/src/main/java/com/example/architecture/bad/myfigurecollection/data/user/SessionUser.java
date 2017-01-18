package com.example.architecture.bad.myfigurecollection.data.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spawn on 06/12/16.
 */

public class SessionUser implements Parcelable {

    private String id;
    private String name;
    private String picture;
    private String homepage;

    public SessionUser(String id, String name, String picture, String homepage) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getHomepage() {
        return homepage;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeString(this.homepage);
    }

    protected SessionUser(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.picture = in.readString();
        this.homepage = in.readString();
    }

    public static final Parcelable.Creator<SessionUser> CREATOR = new Parcelable.Creator<SessionUser>() {
        @Override
        public SessionUser createFromParcel(Parcel source) {
            return new SessionUser(source);
        }

        @Override
        public SessionUser[] newArray(int size) {
            return new SessionUser[size];
        }
    };
}
