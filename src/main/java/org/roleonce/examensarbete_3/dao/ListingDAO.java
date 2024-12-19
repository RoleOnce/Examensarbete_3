package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.repository.ListingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ListingDAO implements ListingDAOInt {

    ListingRepository listingRepository;

    public ListingDAO(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public Optional<Listing> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void save(Listing listing) {
        listingRepository.save(listing);
    }

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    @Transactional
    public List<Listing> getListingByUser(CustomUser user) {
        return listingRepository.findByOwner(user);
    }

    @Override
    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }


    @Override
    public void deleteById(Long id) {
        listingRepository.deleteById(id);
    }
}
