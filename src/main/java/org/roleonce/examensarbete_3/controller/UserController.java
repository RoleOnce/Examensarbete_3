package org.roleonce.examensarbete_3.controller;

import jakarta.validation.Valid;
import org.roleonce.examensarbete_3.dao.UserDAO;
import org.roleonce.examensarbete_3.dto.UserRegistrationDTO;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.roleonce.examensarbete_3.model.Advertisement;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.ImageRepository;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.roleonce.examensarbete_3.service.AdvertisementService;
import org.roleonce.examensarbete_3.service.ImageService;
import org.roleonce.examensarbete_3.service.UserService;
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
import java.util.Base64;
import java.util.List;

@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final ImageService imageService;
    private final UserService userService;
    private final AdvertisementService advertisementService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserDAO userDAO, ImageService imageService, UserService userService, AdvertisementService advertisementService) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.imageService = imageService;
        this.userService = userService;
        this.advertisementService = advertisementService;
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

    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    @PostMapping("/advertisement/add")
    public String createAdvertisement(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("description") String description,
            @ModelAttribute Advertisement advertisement) {

        CustomUser loggedInUser = userService.getLoggedInUser(); // Hämta inloggad användare

        if (loggedInUser == null) {
            return "error"; // Returnera fel-sida om användaren inte är inloggad
        }

        try {
            // Spara bilden och skapa en annons
            advertisement.setFirstName(firstName);
            advertisement.setLastName(lastName);
            advertisement.setDescription(description);
            advertisement.setOwner(loggedInUser); // Koppla annonsen till användaren
            advertisement.setImage(file.getBytes()); // Konvertera bilden till byte[]
            advertisement.setType(file.getContentType()); // Ange MIME-typ

            advertisementService.saveAdvertisement(advertisement); // Spara annonsen
            return "redirect:/advertisements"; // Omdirigera till annonslistan
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Returnera fel-sida vid problem med filuppladdningen
        }
    }

    @GetMapping("/advertisements")
    public String getAdvertisementsForUser(Model model) {
        CustomUser loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            return "error";
        }

        List<Advertisement> advertisements = advertisementService.getAdvertisementsByUser(loggedInUser);
        for (Advertisement ad : advertisements) {
            if (ad.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(ad.getImage());
                ad.setBase64Image(base64Image);
            }
        }

        model.addAttribute("advertisements", advertisements);
        return "advertisements"; // Visa annonser på sidan
    }



    @GetMapping("/advertisement/{id}")
    public String getAdvertisementPage(@PathVariable Long id, Model model) {
        Advertisement advertisement = imageService.getAdvertisementById(id);
        if (advertisement == null) {
            return "error";
        }

        // Konvertera bilden till Base64
        String base64Image = advertisement.getImage() != null
                ? "data:" + advertisement.getType() + ";base64," +
                Base64.getEncoder().encodeToString(advertisement.getImage())
                : null;

        model.addAttribute("advertisement", advertisement);
        model.addAttribute("base64Image", base64Image);

        return "advertisement";
    }

}
