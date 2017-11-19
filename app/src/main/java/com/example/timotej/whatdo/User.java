package com.example.timotej.whatdo;

/**
 * Created by Timotej on 3. 03. 2017.
 */

public class User {
    String idUser;
    String username;
    String email;

    public User(String idUser, String username, String email) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
