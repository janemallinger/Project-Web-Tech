package com.example.frogcrew.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record CrewAssignmentDto(
        Long crewedUserId,
        @NotNull(message = "UserId is required")
        Long userId,
        @NotNull(message = "GameId is required")
        Long gameId,
        @NotBlank(message = "Position is required")
        String position,
        String fullName,
        @NotNull(message = "Report time is required")
        LocalTime reportTime,
        @NotBlank(message = "Report location is required")
        String reportLocation
) {
}

