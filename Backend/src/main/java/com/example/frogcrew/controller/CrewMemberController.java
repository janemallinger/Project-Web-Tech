package com.example.frogcrew.controller;

import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.repository.CrewMemberRepository;
import org.springframework.web.bind.annotation.PostMapping;

public class CrewMemberController {

    private final CrewMemberRepository crewMemberRepository;


    public CrewMemberController(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }
    /**
    @PostMapping()
    public CrewMember createCrewMember(CrewMember crewMember) {}

    **/
}
