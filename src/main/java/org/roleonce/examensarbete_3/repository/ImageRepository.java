package org.roleonce.examensarbete_3.repository;

import org.roleonce.examensarbete_3.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Advertisement, Long> {
}
