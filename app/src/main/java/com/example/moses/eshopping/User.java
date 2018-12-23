package com.example.moses.eshopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String id;
    private List<String> songList = new ArrayList<>();

    public User(String iId, List<String> iSongList) {
        this.id = iId;
        //this.songList = iSongList;
    }

    public User() {
    }

    public String getId() {
        return id;
    }
    public List<String> getSongList() {
        return songList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.readList(songList,String.class.getClassLoader());
    }

    public User(Parcel in) {
        this.id = in.readString();
        in.readList(songList,String.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


}
