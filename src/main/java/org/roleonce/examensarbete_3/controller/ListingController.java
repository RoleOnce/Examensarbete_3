package org.roleonce.examensarbete_3.controller;

import org.roleonce.examensarbete_3.dao.ListingDAO;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.service.ListingService;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class ListingController {

    private final UserService userService;
    private final ListingService listingService;
    private final ListingDAO listingDAO;

    public ListingController(UserService userService, ListingService listingService, ListingDAO listingDAO) {
        this.userService = userService;
        this.listingService = listingService;
        this.listingDAO = listingDAO;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        List<Listing> listings = listingService.getAllListings();

        for (Listing ad : listings) {
            if (ad.getImages() != null && !ad.getImages().isEmpty()) {
                List<String> base64Images = new ArrayList<>();
                for (byte[] b : ad.getImages()) {
                    String base64 = Base64.getEncoder().encodeToString(b);
                    base64Images.add(base64);
                }
                ad.setBase64Image(base64Images);
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
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("description") String description,
            @ModelAttribute Listing listing) {

        CustomUser loggedInUser = userService.getLoggedInUser(); // Hämta inloggad användare

        if (loggedInUser == null) {
            return "error";
        }

        try {
            listing.setDescription(description);
            listing.setOwner(loggedInUser); // Koppla annonsen till användaren

            List<byte[]> imageList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    imageList.add(file.getBytes());
                }
            }
            listing.setImages(imageList);

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
            if (ad.getImages() != null) {
                List<String> base64Images = new ArrayList<>();
                for (byte[] b : ad.getImages()) {
                    String base64 = Base64.getEncoder().encodeToString(b);
                    base64Images.add(base64);
                }
                ad.setBase64Image(base64Images);
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

        // Konvertera varje bild till Base64 och lagra i en lista
        List<String> base64Images = new ArrayList<>();
        if (listing.getImages() != null && !listing.getImages().isEmpty()) {
            for (byte[] image : listing.getImages()) {
                String base64Image = "data:" + listing.getType() + ";base64," +
                        Base64.getEncoder().encodeToString(image);
                base64Images.add(base64Image);
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("listing", listing);
        model.addAttribute("base64Images", base64Images); // Lägg till listan med bilder

        return "listing";
    }

    @GetMapping("/delete-listing")
    public String deleteListing(){

        return "delete-listing";
    }

    @PostMapping("/delete-listing")
    public String deleteListing(@RequestParam Long id, Model model) {

        try {
            listingDAO.deleteById(id);
            model.addAttribute("successMessage", "Listing with id " + id + " was successfully deleted ");
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("errorMessage", "Listing with id " + id + " was not found");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while deleting the listing");
        }
        return "redirect:/";
    }

}
