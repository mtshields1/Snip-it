package com.example.ch.snip_it.users;

import java.util.List;

/**
 * User class to create users for Snip-it
 */
public class User {

    private String username;
    private String password;
    private String email;
    private String id;
    private String dbKey; // Key to find the user in firebase DB under users child
    private List<String> friendsList;

    public User(String username, String password, String email, String id, String dbKey, List<String> friendsList) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.dbKey = dbKey;
        this.friendsList = friendsList;
    }
}