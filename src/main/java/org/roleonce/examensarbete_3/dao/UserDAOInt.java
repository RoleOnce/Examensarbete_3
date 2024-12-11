package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.CustomUser;

import java.util.List;
import java.util.Optional;

public interface UserDAOInt {

    Optional<CustomUser> findByUsername (String username);
    void save(CustomUser customUser);
    List<CustomUser> findAll();
    Optional<CustomUser> findById(Long id);
    void deleteById(Long id);

}
