package org.roleonce.examensarbete_3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.roleonce.examensarbete_3.dao.ListingDAO;
import org.roleonce.examensarbete_3.model.Listing;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.ui.Model;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListingControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ListingDAO listingDAO;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private ListingController listingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listingController = new ListingController(userService, listingDAO);
    }

    @Test
    void homepageShouldShowAllListings() {
        // Arrange
        List<Listing> listings = new ArrayList<>();
        Listing listing1 = new Listing();
        listing1.setTitle("Test Title 1");
        listing1.setPrice(100);
        listings.add(listing1);

        when(listingDAO.findAll()).thenReturn(listings);

        // Act
        String viewName = listingController.homePage(model);

        // Assert
        assertEquals("index", viewName);
        verify(model).addAttribute("listings", listings);
    }

    @Test
    void createListingShouldSaveNewListing() throws Exception {
        // Arrange
        CustomUser user = new CustomUser();
        user.setUsername("testUser");

        Listing listing = new Listing();
        listing.setTitle("New Listing");
        listing.setPrice(200);

        MockMultipartFile file = new MockMultipartFile(
                "files",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        when(userService.getLoggedInUser()).thenReturn(user);
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String viewName = listingController.createListing(
                List.of(file),
                "Test description",
                listing,
                bindingResult,
                model
        );

        // Assert
        assertEquals("redirect:/", viewName);
        verify(listingDAO).save(listing);
        assertEquals(user, listing.getOwner());
        assertEquals("Test description", listing.getDescription());
    }

    @Test
    void getListingShouldShowSingleListing() {
        // Arrange
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setTitle("Test Listing");
        listing.setPrice(300);

        when(listingDAO.findById(listingId)).thenReturn(Optional.of(listing));

        // Act
        String viewName = listingController.editListing(listingId, model);

        // Assert
        assertEquals("edit-listing", viewName);
        verify(model).addAttribute("listing", listing);
    }

    @Test
    void createListingShouldReturnErrorWhenNoUserLoggedIn() throws Exception {
        // Arrange
        when(userService.getLoggedInUser()).thenReturn(null);

        // Act
        String viewName = listingController.createListing(
                new ArrayList<>(),
                "Test description",
                new Listing(),
                bindingResult,
                model
        );

        // Assert
        assertEquals("error", viewName);
    }
}