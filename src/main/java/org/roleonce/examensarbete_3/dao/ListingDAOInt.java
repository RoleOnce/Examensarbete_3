package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingDAOInt {

    Optional<Listing> findByTitle(String title);
    void save(Listing listing);
    List<Listing> findAll();
    List<Listing> getListingByUser(CustomUser user);
    Optional<Listing> findById(Long id);
    void deleteById(Long id);

}
