package org.roleonce.examensarbete_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomUser {

    // TODO - Lägga till FirstName/LastName??? (vid registrering)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "First name must at least have 3 chars")
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50, message = "Last name must at least have 3 chars")
    private String lastName;
    @NotBlank
    @Size(min = 2, max = 50, message = "Username must be between 4-32 chars")
    private String username;
    @NotBlank
    @Size(min = 7, max = 80, message = "Password must be between 7-64 chars")
    private String password;
    @Email
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is Required")
    private UserRole userRole;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Listing> listings = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages = new ArrayList<>();



    // Constructors
    public CustomUser() {

    }
    public CustomUser(String firstName, String lastName, String username, String password, String email, UserRole userRole,  boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }

    public List<String> getListOfPermissions() {
        return userRole.getPermissions();
    }

    public List<Listing> getAdvertisements() {
        return listings;
    }

    public void setAdvertisements(List<Listing> listings) {
        this.listings = listings;
    }

}
