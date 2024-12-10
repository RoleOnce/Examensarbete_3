package org.roleonce.examensarbete_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.roleonce.examensarbete_3.authorities.UserRole;

@Entity
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(min = 2, max = 50, message = "Username must be between 4-32 chars")
    private String username;
    @NotBlank
    @Size(min = 7, max = 80, message = "Password must be between 7-64 chars")
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is Required")
    private UserRole userRole;

    // Constructors
    public CustomUser() {

    }
    public CustomUser(String username, String password, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
