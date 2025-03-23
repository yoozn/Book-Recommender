package com.example.project_ui_implementation.model;

import java.util.HashMap;
import java.util.TreeMap;

public class Books {
    private String title;
    private String author;
    private String genre;
    private String thumbnail;

    //Change the treeMap so that it takes The user username instead of the actual user.
    HashMap<String, Float> ratings;
    private float rate=0;
    private String description;


   // public Books(){};

    public Books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        //this.rate = 0;
        ratings = new HashMap<>();
    }

    public Books(String title, String author, String genre, String thumbnail, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.description = description;
        ratings = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public HashMap<String, Float> getRatings() {
        return ratings;
    }

    //From the values of the Tree, it will calculate the actual rate.
    public float getRate() {
        float sum = 0;
        for (float i : ratings.values()) {
            sum += i;
        }
        return sum / ratings.size();
    }
    public String getDescription() { return description; }

    public void setRating() { this.rate = getRate();}

    //Users will add their rates into the HashMap
    public void addRatingtoTree(String Username, float userRate){
        if (ratings==null){
            ratings = new HashMap<>();
        }
        ratings.put(Username, userRate);
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    @Override
    public String toString() {
        return "Book: " + this.title;
    }
}


