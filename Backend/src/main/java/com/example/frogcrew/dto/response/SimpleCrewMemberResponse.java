package com.example.frogcrew.dto.response;

public record SimpleCrewMemberResponse(
        Long userId,
        String fullName,
        String email,
        String phoneNumber
) {}