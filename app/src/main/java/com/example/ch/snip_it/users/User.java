package com.example.ch.snip_it.users;

/**
 * User class to create users for Snip-it
 */
public class User {

    public String username;
    public String password;
    public String email;
    public String id;
    public String dbKey; // Key to find the user in firebase DB under users child

    public User(String username, String password, String email, String id, String dbKey) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.dbKey = dbKey;
    }
}