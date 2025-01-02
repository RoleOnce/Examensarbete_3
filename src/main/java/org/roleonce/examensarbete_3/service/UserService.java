package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.config.CustomUserDetails;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.ListingRepository;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    ListingRepository listingRepository;
    UserRepository userRepository;

    public UserService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    // Hämta inloggad användare från Spring Security
    public CustomUser getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getCustomUser();
        }

        return null; // Om ingen användare är inloggad
    }

    public String getUsernameByListingId(Long adId) {

        Listing listing = listingRepository.findById(adId).orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));

        return listing.getOwner().getUsername();
    }

    public String getEmailByListingId(Long adId) {

        Listing listing = listingRepository.findById(adId).orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));

        return listing.getOwner().getEmail();
    }

    public Optional<CustomUser> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public CustomUser findById(Long recipientId) {
        return userRepository.findById(recipientId).orElseThrow(() -> new IllegalArgumentException("Recipient not found"));
    }
}
