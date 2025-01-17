package org.roleonce.examensarbete_3.controller;

import jakarta.validation.Valid;
import org.roleonce.examensarbete_3.dao.ListingDAO;
import org.roleonce.examensarbete_3.dao.UserDAO;
import org.roleonce.examensarbete_3.dto.UserRegistrationDTO;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final ListingDAO listingDAO;
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserDAO userDAO, ListingDAO listingDAO, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.listingDAO = listingDAO;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {

        model.addAttribute("userDTO", new CustomUser());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute(name = "userDTO") UserRegistrationDTO userDTO,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "register";
        }
        if (userDAO.findByUsername(userDTO.username()).isPresent()) {
            model.addAttribute("usernameError", "Username is already taken");
            model.addAttribute("roles", UserRole.values());
            return "register";
        }

        try {
            CustomUser newUser = new CustomUser(
                    userDTO.firstName(),
                    userDTO.lastName(),
                    userDTO.username(),
                    passwordEncoder.encode(userDTO.password()),
                    userDTO.email(),
                    UserRole.USER,
                    true,
                    true,
                    true,
                    true
            );

            System.out.println("User named: '" + userDTO.username() + "' has been registered");
            userDAO.save(newUser);

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("usernameError", "Username is already taken.");
            model.addAttribute("roles", UserRole.values());
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/delete-user")
    public String deleteUser() {

        return "delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUserPost(@RequestParam Long id, Model model) {

        try {
            userDAO.deleteById(id);
            model.addAttribute("successMessage", "User with id " + id + " was successfully deleted ");
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("errorMessage", "User with id " + id + " was not found");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while deleting the user");
        }

        return "redirect:/";
    }

}
