package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingDAOInt {

    Optional<Listing> findByTitle(String title);
    void save(Listing listing);
    List<Listing> findAll();
    Optional<Listing> findById(int id);
    void deleteById(Long id);

}
