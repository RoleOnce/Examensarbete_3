package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Transactional
    public void saveListing(Listing listing) {
        listingRepository.save(listing);
    }

    @Transactional
    public List<Listing> getListingByUser(CustomUser user) {
        return listingRepository.findByOwner(user);
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }
}
