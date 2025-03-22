package com.example.project_ui_implementation.model;

import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Objects;

//Implementation of Serializable is for passing objects from one Activity to the other
public class Users implements Serializable {
    //List to keep a history of the Genre.
    private ArrayList<String> Genre;

    //Username corresponding to one person
    private String Username;

    //Password for a user
    private String Password;

    //Default constructor of the class.
    public Users(){};

    //Constructor of the class.
    public Users(String wUsername, String wPassword){
        Username=wUsername;
        Password=wPassword;
        Genre=new ArrayList<String>();
    }

    public String getPassword() {
        return Password;
    }
    public String getUsername() {
        return Username;
    }
    public ArrayList<String> getGenre() {
        return Genre;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setGenre(ArrayList<String>S){
        Genre = S;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void addGenre(String nGenre){
        Genre.add(nGenre);
    }
    @Override
    public String toString(){
        return "User: "+Username;
    }
}
