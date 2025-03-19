package com.example.project_ui_implementation.model;

public class Books {
    private String title;
    private String author;
    private String genre;
    private String thumbnail;


    public Books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Books(String title, String author, String genre, String thumbnail) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
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

}


