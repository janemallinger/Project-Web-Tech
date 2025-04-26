package com.example.frogcrew.dto;

public record SimpleCrewMemberResponse(
        Long userId,
        String fullName,
        String email,
        String phoneNumber
) {}