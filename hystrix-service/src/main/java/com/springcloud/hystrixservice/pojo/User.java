package com.springcloud.hystrixservice.pojo;

public class User {
    private Long id;
    private String username;
    private String passwork;

    public User() {
    }

    public User(Long id, String username, String passwork) {
        this.id = id;
        this.username = username;
        this.passwork = passwork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
