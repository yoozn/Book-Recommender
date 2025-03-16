package com.example.project_ui_implementation.model;

public class Books {
    private String title;
    private String author;
    private String genre;


    public Books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
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


