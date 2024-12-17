package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.config.CustomUserDetails;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.ListingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    ListingRepository listingRepository;

    public UserService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
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

}
