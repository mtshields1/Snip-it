package com.example.ch.snip_it.users;

/**
 * Created by Owner on 5/20/2018.
 */

public class User {

    public String username;
    public String password;
    public String email;
    public String id;

    public User(String username, String password, String email, String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }
}