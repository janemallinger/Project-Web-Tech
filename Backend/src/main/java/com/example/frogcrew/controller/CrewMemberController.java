package com.example.frogcrew.controller;

import com.example.frogcrew.service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;


    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }
//    @PostMapping("")
//    public CrewMember createCrewMember(CrewMember crewMember) {
//
//    }

    @PostMapping
    public Result createCrewMember(@PathVariable ){

    }
    @GetMapping
    public Result findAllCrewMembers() {
        List<CrewMember> members = this.crewMemberService.findAll();

        if (members.isEmpty()) {
            return new Result(true, HttpStatus.NO_CONTENT.value(), "No members found");
        }

        //use dto to return objects

        return null;
    }

}
