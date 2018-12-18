package com.example.moses.eshopping;

public class Song {
    private String m_Name;
    private String m_Album;
    private String m_File;
    private String m_Artist;
    private Double m_Price;
    private String m_Clip;

    //Getters
    public String getFile() {
        return m_File;
    }
    public String getName() {
        return m_Name;
    }

    public Song(String name, String album, String file, String artist, Double price, String clip) {
        this.m_Name = name;
        this.m_Album = album;
        this.m_File = file;
        this.m_Artist = artist;
        this.m_Price = price;
        this.m_Clip = clip;
    }

    public String getAlbum() {
        return m_Album;
    }
    public String getArtist() {
        return m_Artist;
    }
    public Double getPrice() {
        return m_Price;
    }
    public String getClip() {
        return m_Clip;
    }
    //End Getters

    public Song() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
