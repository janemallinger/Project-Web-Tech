package com.example.frogcrew.availability.dto;

import jakarta.validation.constraints.NotNull;

public record AvailabilityDto(
        @NotNull(message = "userId is required.")
        Long userId,
        @NotNull(message = "gameId is required.")
        Long gameId,
        @NotNull(message = "Available is required.")
        Boolean available,
        String comment
) {

}
