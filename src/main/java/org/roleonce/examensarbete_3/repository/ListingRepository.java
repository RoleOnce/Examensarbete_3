package org.roleonce.examensarbete_3.repository;

import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByOwner(CustomUser owner);

}

