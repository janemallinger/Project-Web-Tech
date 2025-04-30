package com.example.frogcrew.crewmember.dto;

public record LoginDTO (
        Long userId,
        String role,
        String token
){
}
