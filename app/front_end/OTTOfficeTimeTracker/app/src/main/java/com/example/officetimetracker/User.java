package com.example.officetimetracker;

public class User {

    private static User userInstance = null;

    private String username;
    private String email;
    private String password;
    private String id;


    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User getInstance(String username, String password) {

        if (userInstance == null) {
            userInstance = new User(username, password);
        }
        return userInstance;
    }

    public static void releaseInstance() {
        userInstance = null;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }
}
