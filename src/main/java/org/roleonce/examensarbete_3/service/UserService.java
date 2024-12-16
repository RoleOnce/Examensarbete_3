package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.config.CustomUserDetails;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Hämta inloggad användare från Spring Security
    public CustomUser getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getCustomUser();
        }

        return null; // Om ingen användare är inloggad
    }

    public String getUsername(Long user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

}
