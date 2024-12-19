package org.roleonce.examensarbete_3.controller;

import org.roleonce.examensarbete_3.dao.ListingDAO;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ListingController {

    private final UserService userService;
    private final ListingDAO listingDAO;

    public ListingController(UserService userService, ListingDAO listingDAO) {
        this.userService = userService;
        this.listingDAO = listingDAO;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        List<Listing> listings = listingDAO.findAll();

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

    // This endpoint is used to list an item
    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    // This endpoint is used to list an item
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

            listingDAO.save(listing);
            return "redirect:/";

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // This endpoint shows the listings of the user who´s logged in at the moment
    @GetMapping("/listings")
    public String getListingsForUser(Model model) {
        CustomUser loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            return "error";
        }

        // TODO - Måste fixa html filen
        List<Listing> listings = listingDAO.getListingByUser(loggedInUser);
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
        return "my-listings";
    }

    // This endpoint shows listings individually
    @GetMapping("/listing/{id}")
    public String getListingPage(@PathVariable Long id, Model model, Authentication authentication) {

        Optional<Listing> optionalListing = listingDAO.findById(id);
        if (optionalListing.isEmpty()) {
            return "error";
        }

        Listing listing = optionalListing.get();

        String username = userService.getUsernameByListingId(id);
        String currentUser = authentication.getName();
        boolean isOwner = currentUser.equals(username);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

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
        model.addAttribute("base64Images", base64Images);
        model.addAttribute("canDelete", isAdmin || isOwner);

        return "listing";
    }

    // This endpoint deletes listings with id´s
    // --> This endpoint might be deleted soon.. <--
    @GetMapping("/delete-listing")
    public String deleteListing(){

        return "delete-listing";
    }

    // This endpoint deletes listings individually in "listing/{id}"-GET-endpoint with used of its id
    @PostMapping("/listing/{id}/delete")
    public String deleteListingPost(@PathVariable Long id, Authentication authentication) {

        Optional<Listing> optionalListing = listingDAO.findById(id);
        if (optionalListing.isEmpty()) {
            return "error";
        }

        Listing listing = optionalListing.get();

        String username = authentication.getName();
        boolean isOwner = username.equals(listing.getOwner().getUsername());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isOwner || isAdmin) {
            listingDAO.deleteById(id);
            return "redirect:/";
        } else {
            return "error";
        }
    }

    // This endpoint is used to edit an already existing listing
    @GetMapping("/listing/{id}/edit")
    public String editListing(@PathVariable Long id, Model model) {
        Optional<Listing> optionalListing = listingDAO.findById(id);
        if (optionalListing.isEmpty()) {
            return "error";
        }

        Listing listing = optionalListing.get();

        model.addAttribute("listing", listing);

        return "edit-listing";
    }

    // This endpoint is used to save the updates on the listing
    @PostMapping("/listing/{id}/edit")
    public String saveEditListing(@PathVariable Long id,
                                  @RequestParam String description,
                                  @RequestParam("files") List<MultipartFile> files) throws IOException {

        Optional<Listing> optionalListing = listingDAO.findById(id);
        if (optionalListing.isEmpty()) {
            return "error";
        }

        Listing listing = optionalListing.get();
        listing.setDescription(description);

        List<byte[]> updateImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                updateImages.add(file.getBytes());
            }
        }

        if (!updateImages.isEmpty()) {
            listing.setImages(updateImages);
        }

        listingDAO.save(listing);

        return "redirect:/listing/" + id;
    }

}
