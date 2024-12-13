package org.roleonce.examensarbete_3.controller;

import jakarta.validation.Valid;
import org.roleonce.examensarbete_3.dao.UserDAO;
import org.roleonce.examensarbete_3.dto.UserRegistrationDTO;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
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
                    userDTO.username(),
                    passwordEncoder.encode(userDTO.password()),
                    userDTO.email(),
                    userDTO.userRole() != null ? userDTO.userRole() : UserRole.USER,
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

}
