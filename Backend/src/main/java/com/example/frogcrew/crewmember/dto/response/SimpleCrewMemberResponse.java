package com.example.frogcrew.crewmember.dto.response;

public record SimpleCrewMemberResponse(
        Long userId,
        String fullName,
        String email,
        String phoneNumber
) {}