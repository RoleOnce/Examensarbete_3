package org.roleonce.examensarbete_3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CustomUser {

    @Id
    private long id;
    private String username;
    private String password;
    private String email;

    // Constructors
    public CustomUser() {

    }
    public CustomUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
