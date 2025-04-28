package com.example.frogcrew.game.dto.GameResponse;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GameResponseDto(
        Long gameId,
        Long scheduleId,
        LocalDate gameDate,
        String venue,
        String opponent,
        Boolean isFinalized

) {
}
