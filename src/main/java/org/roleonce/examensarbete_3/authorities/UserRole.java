package org.roleonce.examensarbete_3.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.roleonce.examensarbete_3.authorities.UserPermission.*;

public enum UserRole {
    GUEST(GET),
    USER(GET, POST),
    ADMIN(GET, POST, PUT, DELETE);

    private final List<String> permissions;

    UserRole(UserPermission... permissionsList) {
        this.permissions = Arrays.stream(permissionsList)
                .map(UserPermission::getPermission)
                .toList();
    }

    public List<String> getPermissions() {

        return permissions;
    }

    // Denna metod skapar och returnerar en lista med behörigheter som används
    // av Spring Security för att hantera användarens roller och rättigheter.
    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        authorities.addAll(getPermissions().stream().map(SimpleGrantedAuthority::new).toList());

        return authorities;
    }

}
