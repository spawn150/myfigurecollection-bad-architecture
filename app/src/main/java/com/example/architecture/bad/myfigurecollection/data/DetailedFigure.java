package com.example.architecture.bad.myfigurecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spawn on 25/06/16.
 */
public class DetailedFigure implements Parcelable {

    private String id;
    private String name;
    private String imageUrl;
    private String category;
    private String author;
    private String releaseDate;
    private String price;
    private String wishability;
    private String score;
    private String number;
    private String barcode;

    private DetailedFigure(String id, String name, String imageUrl, String category, String author, String releaseDate, String price, String wishability, String score, String number, String barcode) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.author = author;
        this.releaseDate = releaseDate;
        this.price = price;
        this.wishability = wishability;
        this.score = score;
        this.number = number;
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPrice() {
        return price;
    }

    public String getWishability() {
        return wishability;
    }

    public String getScore() {
        return score;
    }

    public String getNumber() {
        return number;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public String toString() {
        return "DetailedFigure{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", price='" + price + '\'' +
                ", wishability='" + wishability + '\'' +
                ", score='" + score + '\'' +
                ", number='" + number + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }

    public static class Builder {
        private String id;
        private String name;
        private String imageUrl;
        private String category;
        private String author;
        private String releaseDate;
        private String price;
        private String wishability;
        private String score;
        private String number;
        private String barcode;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setWishability(String wishability) {
            this.wishability = wishability;
            return this;
        }

        public Builder setScore(String score) {
            this.score = score;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setBarcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public DetailedFigure build() {
            return new DetailedFigure(id, name, imageUrl, category, author, releaseDate, price, wishability, score, number, barcode);
        }
    }

    protected DetailedFigure(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        author = in.readString();
        releaseDate = in.readString();
        price = in.readString();
        wishability = in.readString();
        score = in.readString();
        number = in.readString();
        barcode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeString(author);
        dest.writeString(releaseDate);
        dest.writeString(price);
        dest.writeString(wishability);
        dest.writeString(score);
        dest.writeString(number);
        dest.writeString(barcode);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DetailedFigure> CREATOR = new Parcelable.Creator<DetailedFigure>() {
        @Override
        public DetailedFigure createFromParcel(Parcel in) {
            return new DetailedFigure(in);
        }

        @Override
        public DetailedFigure[] newArray(int size) {
            return new DetailedFigure[size];
        }
    };
}