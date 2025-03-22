package com.example.project_ui_implementation.model;

public class Books {
    private String title;
    private String author;
    private String genre;
    private String thumbnail;
    private int rate;
    private String description;


    public Books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rate = 0;
    }

    public Books(String title, String author, String genre, String thumbnail, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.description = description;
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

    public int getRate() { return rate;}
    public String getDescription() { return description; }

    public void setRating(int rate) { this.rate = rate;}

}


