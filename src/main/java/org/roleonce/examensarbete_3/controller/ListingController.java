package org.roleonce.examensarbete_3.controller;

import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.service.ListingService;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class ListingController {

    private final UserService userService;
    private final ListingService listingService;

    public ListingController(UserService userService, ListingService listingService) {
        this.userService = userService;
        this.listingService = listingService;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        List<Listing> listings = listingService.getAllListings();

        for (Listing ad : listings) {
            if (ad.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(ad.getImage());
                ad.setBase64Image(base64Image);
            }
        }

        model.addAttribute("listings", listings);
        return "index";
    }

    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    @PostMapping("/upload")
    public String createListing(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @ModelAttribute Listing listing) {

        CustomUser loggedInUser = userService.getLoggedInUser(); // Hämta inloggad användare

        if (loggedInUser == null) {
            return "error";
        }

        try {

            listing.setDescription(description);
            listing.setOwner(loggedInUser); // Koppla annonsen till användaren
            listing.setImage(file.getBytes()); // Konvertera bilden till byte[]
            listing.setType(file.getContentType()); // Ange MIME-typ

            listingService.saveListing(listing);
            return "redirect:/";

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/listings")
    public String getListingsForUser(Model model) {
        CustomUser loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            return "error";
        }

        List<Listing> listings = listingService.getListingByUser(loggedInUser);
        for (Listing ad : listings) {
            if (ad.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(ad.getImage());
                ad.setBase64Image(base64Image);
            }
        }

        model.addAttribute("listings", listings);
        return "my-listings"; // Visar annonser för inloggad användare
    }

    @GetMapping("/listing/{id}")
    public String getListingPage(@PathVariable Long id, Model model) {

        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return "error";
        }

        String username = userService.getUsernameByListingId(id);

        // Konvertera bilden till Base64
        String base64Image = listing.getImage() != null
                ? "data:" + listing.getType() + ";base64," +
                Base64.getEncoder().encodeToString(listing.getImage())
                : null;

        model.addAttribute("username", username);
        model.addAttribute("listing", listing);
        model.addAttribute("base64Image", base64Image);

        return "listing";
    }

}
