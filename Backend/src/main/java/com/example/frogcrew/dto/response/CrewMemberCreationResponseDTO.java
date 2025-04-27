package com.example.frogcrew.dto.response;

import java.util.List;

public record CrewMemberCreationResponseDTO(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String role,
        List<String> positions
) {
}
