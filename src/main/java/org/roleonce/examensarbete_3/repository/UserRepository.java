package org.roleonce.examensarbete_3.repository;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
}
