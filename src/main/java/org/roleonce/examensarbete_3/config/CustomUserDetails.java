package org.roleonce.examensarbete_3.config;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final CustomUser customUser;

    public CustomUserDetails(CustomUser customUser) {
        this.customUser = customUser;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return customUser.getPassword();
    }

    @Override
    public String getUsername() {
        return customUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return customUser.isEnabled();
    }
}
