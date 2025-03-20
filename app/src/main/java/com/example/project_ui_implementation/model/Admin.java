package com.example.project_ui_implementation.model;

public class Admin {
    private String adminID;

    private String Username;

    private String adminPassword;

    public Admin(){}

    public Admin(String wUsername, String wadminPassword, String wadminID){
        adminID = wadminID;
        Username = wUsername;
        adminPassword = wadminPassword;
    };

    public String getAdminID(){
        return adminID;
    }
    public String getAdminPassword(){
        return adminPassword;
    }
    public String getUsername(){
        return Username;
    }

    public void setUsername(String wUsername){
        Username = wUsername;
    }
    public void setAdminID(String wadminID){
        adminID = wadminID;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString(){
        return "Admin: " + adminID;
    }

}
