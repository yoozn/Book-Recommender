package com.example.project_ui_implementation.model;

import java.util.HashMap;
import java.util.TreeMap;

public class Books {
    private String title;
    private String author;
    private String genre;
    private String thumbnail;

    //Change the treeMap so that it takes The user username instead of the actual user.
    HashMap<String, Integer> ratings;
    private int rate;
    private String description;


    public Books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        //this.rate = 0;
    }

    public Books(String title, String author, String genre, String thumbnail, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.description = description;
        this.ratings = new HashMap<>();
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

    public double getRate() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0f;
        }
        double sum = 0;
        for (int i : ratings.values()) {
            sum += i;
        }
        return sum / ratings.size();
    }
    public String getDescription() { return description; }

    public void setRating(int rate) { this.rate = rate;}

}


