package com.example.project_ui_implementation.model;

import java.util.ArrayList;

public class Book {

    private String bookTitle;

    private ArrayList<String> Authors;

    private String thumbnail;

    private ArrayList<String> Subjects;

    //Deffault constructor of the class.
    public Book(){
        bookTitle = "Title1";
        Authors = new ArrayList<>();
        thumbnail = "something";
        Subjects = new ArrayList<>();
    }

    //Constructor of the actual class
    public Book(String wTitle, ArrayList<String> wAuthors, String wThumbnail,ArrayList<String> wSubject){
        bookTitle = wTitle;
        Authors = wAuthors;
        thumbnail = wThumbnail;
        Subjects = wSubject;
    }

    public String getBookTitleTitle(){
        return bookTitle;
    }
    public ArrayList<String> getAuthors() {
        return Authors;
    }
    public ArrayList<String> getSubjects(){
        return Subjects;
    }
    public String getThumbnail(){
        return thumbnail;
    }
    public void setAuthors(ArrayList<String>S){
        Authors = S;
    }
    public void setBookTitle(String wTitle){
        bookTitle = wTitle;
    }
    public void setThumbnail(String wThumbnail){
        wThumbnail = thumbnail;
    }
    public void setSubjects(ArrayList<String> S) {
        Subjects = S;
    }
}
