package com.example.architecture.bad.myfigurecollection.data.figures;

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
    private String widthResolution;
    private String heightResolution;
    private String size;
    private String hits;

    private DetailedFigure(String id, String name, String imageUrl, String category, String author, String releaseDate, String price, String wishability, String score, String number, String barcode, String widthResolution, String heightResolution, String size, String hits) {
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
        this.widthResolution = widthResolution;
        this.heightResolution = heightResolution;
        this.size = size;
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

    public String getWidthResolution() {
        return widthResolution;
    }

    public String getHeightResolution() {
        return heightResolution;
    }

    public String getSize() {
        return size;
    }

    public String getHits() {
        return hits;
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
                ", widthResolution='" + widthResolution + '\'' +
                ", heightResolution='" + heightResolution + '\'' +
                ", size='" + size + '\'' +
                ", hits='" + hits + '\'' +
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
        private String widthResolution;
        private String heightResolution;
        private String size;
        private String hits;

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

        public Builder setWidthResolution(String widthResolution) {
            this.widthResolution = widthResolution;
            return this;
        }

        public Builder setHeightResolution(String heightResolution) {
            this.heightResolution = heightResolution;
            return this;
        }

        public Builder setSize(String size) {
            this.size = size;
            return this;
        }

        public Builder setHits(String hits) {
            this.hits = hits;
            return this;
        }

        public DetailedFigure build() {
            return new DetailedFigure(id, name, imageUrl, category, author, releaseDate, price, wishability, score, number, barcode, widthResolution, heightResolution, size, hits);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.category);
        dest.writeString(this.author);
        dest.writeString(this.releaseDate);
        dest.writeString(this.price);
        dest.writeString(this.wishability);
        dest.writeString(this.score);
        dest.writeString(this.number);
        dest.writeString(this.barcode);
        dest.writeString(this.widthResolution);
        dest.writeString(this.heightResolution);
        dest.writeString(this.size);
        dest.writeString(this.hits);
    }

    protected DetailedFigure(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.category = in.readString();
        this.author = in.readString();
        this.releaseDate = in.readString();
        this.price = in.readString();
        this.wishability = in.readString();
        this.score = in.readString();
        this.number = in.readString();
        this.barcode = in.readString();
        this.widthResolution = in.readString();
        this.heightResolution = in.readString();
        this.size = in.readString();
        this.hits= in.readString();
    }

    public static final Creator<DetailedFigure> CREATOR = new Creator<DetailedFigure>() {
        @Override
        public DetailedFigure createFromParcel(Parcel source) {
            return new DetailedFigure(source);
        }

        @Override
        public DetailedFigure[] newArray(int size) {
            return new DetailedFigure[size];
        }
    };
}