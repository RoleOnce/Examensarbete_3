package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.Listing;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ListingDAO implements ListingDAOInt {
    @Override
    public Optional<Listing> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void save(Listing listing) {

    }

    @Override
    public List<Listing> findAll() {
        return List.of();
    }

    @Override
    public Optional<Listing> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
