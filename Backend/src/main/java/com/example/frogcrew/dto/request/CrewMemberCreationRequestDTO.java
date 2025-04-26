package com.example.frogcrew.dto.request;


import jakarta.validation.constraints.NotBlank; // Generally preferred for Strings
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;    // For email format validation
import jakarta.validation.constraints.NotEmpty;   // Still correct for the List

import java.util.List;

public record CrewMemberCreationRequestDTO(

        @NotBlank(message = "First Name is required.")
        String firstName,

        @NotBlank(message = "Last Name is required.")
        String lastName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.") // Add @Email validation
        String email,

        @NotBlank(message = "Phone Number is required.") // Use NotBlank
        @Pattern(regexp = "\\d{10}|\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be 10 digits or 999-999-9999 format")
        String phoneNumber,

        @NotBlank(message = "Password is required") // Use NotBlank
        String password,

        @NotBlank(message = "Role is required.")
        String role,

        @NotEmpty(message = "Positions is required.")
        List<String> position // Changed field name to match the example JSON key
){

}