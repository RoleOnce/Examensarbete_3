package org.roleonce.examensarbete_3.dao;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO implements UserDAOInt {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<CustomUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Override
    public List<CustomUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<CustomUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
