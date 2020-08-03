package com.springcloud.ribbonserivce.pojo;

public class User {
    private int id;
    private String username;
    private String passwork;

    public User() {
    }

    public User(int id, String username, String passwork) {
        this.id = id;
        this.username = username;
        this.passwork = passwork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }
}
