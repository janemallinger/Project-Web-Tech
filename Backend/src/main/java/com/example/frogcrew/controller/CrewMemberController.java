package com.example.frogcrew.controller;

import com.example.frogcrew.Service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


public class CrewMemberController {

    private final CrewMemberService crewMemberService;


    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }
//    @PostMapping("")
//    public CrewMember createCrewMember(CrewMember crewMember) {
//
//    }
    @GetMapping
    public Result findAllCrewMembers() {
        List<CrewMember> members = this.crewMemberService.findAll();

        if (members.isEmpty()) {
            return new Result(true, HttpStatus.NO_CONTENT, "No members found");
        }

        //use dto to return object

        return null;
    }

}
