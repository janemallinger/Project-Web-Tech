package com.example.frogcrew.controller;

import com.example.frogcrew.Service.CrewMemberService;


public class CrewMemberController {

    private final CrewMemberService CrewMemberService;


    public CrewMemberController(CrewMemberService CrewMemberService) {
        this.CrewMemberService = CrewMemberService;
    }
    /**
    @PostMapping()
    public CrewMember createCrewMember(CrewMember crewMember) {}

    **/
}
