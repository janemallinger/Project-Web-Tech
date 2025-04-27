package com.example.frogcrew.game.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GameDTO(
        @NotNull(message = "date is required")
        LocalDate gameDate,
        @NotEmpty(message =  "venue is required")
        String venue,
        @NotEmpty(message =  "opponent is required")
        String opponent

) {
}
