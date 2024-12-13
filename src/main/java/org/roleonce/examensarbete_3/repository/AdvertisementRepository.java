package org.roleonce.examensarbete_3.repository;

import org.roleonce.examensarbete_3.model.Advertisement;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByOwner(CustomUser owner);

}

