package com.example.frogcrew.assignment.dto;

import java.util.List;

public record CrewListDto(
        Long gameId,
        String gameStart,
        String gameDate,
        String venue,
        String opponent,
        List<CrewAssignmentDto> crewedMembers
) {
}