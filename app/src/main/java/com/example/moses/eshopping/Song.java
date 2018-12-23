package com.example.moses.eshopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Song implements Parcelable  {
    public static final String TAG = "$Song$";

    private String name;
    private String album;
    private String thumbImage;
    private String file;
    private String artist;
    private Double price;
    private String clip;
    private String genre;

    //Getters
    public String getFile() {
        return file;
    }
    public String getName() {
        return name;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String newName) {
         album = newName;
    }
    public String getArtist() {
        return artist;
    }
    public Double getPrice() {
        return price;
    }
    public String getClip() {
        return clip;
    }
    public String getGenre() { return genre; }
    public String getThumbImage() { return thumbImage; }
    //End Getters

    public Song() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(album);
        dest.writeString(thumbImage);
        dest.writeString(file);
        dest.writeString(artist);
        dest.writeString(clip);
        dest.writeString(genre);
        dest.writeDouble(price);
    }

    public Song(Parcel in) {
        this.name = in.readString();
        this.album = in.readString();
        this.thumbImage = in.readString();
        this.file = in.readString();
        this.artist = in.readString();
        this.clip = in.readString();
        this.genre = in.readString();
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
