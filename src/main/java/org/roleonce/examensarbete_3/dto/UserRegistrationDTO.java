package org.roleonce.examensarbete_3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.roleonce.examensarbete_3.authorities.UserRole;

public record UserRegistrationDTO(

        @NotBlank
        @Size(min = 3, max = 50, message = "First name must at least have 3 chars")
        String firstName,

        @NotBlank
        @Size(min = 3, max = 50, message = "Last name must at least have 3 chars")
        String lastName,

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 7, max = 80, message = "Password must be between 7 and 80 characters")
        String password,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        UserRole userRole

) {

}