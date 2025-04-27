package com.example.frogcrew.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;    // For email format validation
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CrewMemberCreationRequestDTO(

        @NotBlank(message = "First Name is required.")
        String firstName,

        @NotBlank(message = "Last Name is required.")
        String lastName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Phone Number is required.") // Use NotBlank
        @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
        String phoneNumber,

        @NotBlank(message = "Password is required") // Use NotBlank
        String password,

        @NotBlank(message = "Role is required.")
        String role,

        @NotEmpty(message = "Positions is required.")
        List<String> positions
){

}