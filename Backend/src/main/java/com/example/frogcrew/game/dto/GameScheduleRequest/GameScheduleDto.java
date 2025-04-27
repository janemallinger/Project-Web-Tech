package com.example.frogcrew.game.dto.GameScheduleRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record GameScheduleDto(
        @NotEmpty(message = "sport is required")
        String sport,
        @NotEmpty(message = "season is required")
        @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "must be in the format YYYY-YYYY")
        String season
) {

}
