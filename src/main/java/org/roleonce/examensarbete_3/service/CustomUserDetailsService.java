package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// Används i SecurityConfig
// Hämtar användardata från DB för att kunna logga in
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("Authorities for user: " + customUser.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                customUser.getUsername(),
                customUser.getPassword(),
                customUser.isEnabled(),
                customUser.isAccountNonExpired(),
                customUser.isCredentialsNonExpired(),
                customUser.isAccountNonLocked(),
                customUser.getAuthorities()
        );
    }
}
