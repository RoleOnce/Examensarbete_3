package org.roleonce.examensarbete_3.controller;

import jakarta.validation.Valid;
import org.roleonce.examensarbete_3.dao.UserDAO;
import org.roleonce.examensarbete_3.dto.UserRegistrationDTO;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.ImageRepository;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.roleonce.examensarbete_3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final ImageService imageService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserDAO userDAO, ImageService imageService) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String index() {

        return "index";
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
                    userDTO.userRole() != null? userDTO.userRole() : UserRole.USER,
                    true,
                    true,
                    true,
                    true
            );

            System.out.println("User named: '" + userDTO.username() + "' created");
            userDAO.save(newUser);

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("usernameError", "Username is already taken.");
            model.addAttribute("roles", UserRole.values());
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload"; // Renderar upload.html
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("description") String description) {
        try {
            imageService.saveImage(file, firstName, lastName, description);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }


}
