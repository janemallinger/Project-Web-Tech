package com.example.frogcrew.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CrewMembers")
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @NotEmpty(message = "First Name may not be empty")
    private String firstName;

    @NotEmpty(message = "Last Name may not be empty")
    private String lastName;

    @Column(unique = true)
    @NotEmpty(message = "Email may not be empty")
    private String email;

    @NotEmpty(message = "Role may not be empty")
    private String role;

    @NotEmpty(message = "Password may not be empty")
    private String password;

    @Size(min = 10, max = 10, message = "Phone Number must be 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone Number must contain only digits")
    @NotEmpty(message = "Phone Number may not be empty")
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> qualifiedPositions;

    public CrewMember() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getQualifiedPositions() {
        return qualifiedPositions;
    }

    public void setQualifiedPositions(List<String> qualifiedPositions) {
        this.qualifiedPositions = qualifiedPositions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
