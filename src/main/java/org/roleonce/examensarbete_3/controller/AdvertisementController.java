package org.roleonce.examensarbete_3.controller;

import org.roleonce.examensarbete_3.model.Advertisement;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.service.AdvertisementService;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class AdvertisementController {

    private final UserService userService;
    private final AdvertisementService advertisementService;

    public AdvertisementController(UserService userService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();

        for (Advertisement ad : advertisements) {
            if (ad.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(ad.getImage());
                ad.setBase64Image(base64Image);
            }
        }

        model.addAttribute("advertisements", advertisements);
        return "index";
    }

    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    @PostMapping("/upload")
    public String createAdvertisement(
            @RequestParam("file") MultipartFile file,
            //@RequestParam("firstName") String firstName,
            //@RequestParam("lastName") String lastName,
            @RequestParam("description") String description,
            @ModelAttribute Advertisement advertisement) {

        CustomUser loggedInUser = userService.getLoggedInUser(); // Hämta inloggad användare

        if (loggedInUser == null) {
            return "error";
        }

        try {

            //advertisement.setFirstName(firstName);
            //advertisement.setLastName(lastName);
            advertisement.setDescription(description);
            advertisement.setOwner(loggedInUser); // Koppla annonsen till användaren
            advertisement.setImage(file.getBytes()); // Konvertera bilden till byte[]
            advertisement.setType(file.getContentType()); // Ange MIME-typ

            advertisementService.saveAdvertisement(advertisement);
            return "redirect:/advertisements";

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
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
        return "myAdvertisements"; // Visar annonser för inloggad användare
    }

    @GetMapping("/advertisement/{id}")
    public String getAdvertisementPage(@PathVariable Long id, Model model) {

        String username = userService.getUsername(id);
        Advertisement advertisement = advertisementService.getAdvertisementById(id);
        if (advertisement == null) {
            return "error";
        }

        // Konvertera bilden till Base64
        String base64Image = advertisement.getImage() != null
                ? "data:" + advertisement.getType() + ";base64," +
                Base64.getEncoder().encodeToString(advertisement.getImage())
                : null;

        model.addAttribute("username", username);
        model.addAttribute("advertisement", advertisement);
        model.addAttribute("base64Image", base64Image);

        return "advertisement";
    }

}
